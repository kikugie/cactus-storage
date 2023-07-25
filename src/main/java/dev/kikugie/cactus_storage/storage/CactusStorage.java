package dev.kikugie.cactus_storage.storage;

import dev.kikugie.cactus_storage.util.ConverterIterator;
import dev.kikugie.cactus_storage.util.RandomizedIterator;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtLong;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

@SuppressWarnings("UnstableApiUsage")
public class CactusStorage extends SnapshotParticipant<CactusStorage.Contents> implements Storage<ItemVariant> {
    private static CactusStorage GLOBAL_STORAGE;
    private static final String STORAGE_KEY = "storage";
    private Contents contents;

    public CactusStorage() {
        this.contents = new Contents();
    }

    public CactusStorage(Contents contents) {
        this.contents = contents;
    }

    public static void load(NbtCompound nbt) {
        NbtList list = nbt.getList(STORAGE_KEY, nbt.getType());
        GLOBAL_STORAGE = new CactusStorage(Contents.deserialize(list));
    }

    public static NbtCompound save(NbtCompound nbt) {
        nbt.put(STORAGE_KEY, GLOBAL_STORAGE.contents.serialize());
        return nbt;
    }

    @Nullable
    public static Storage<ItemVariant> get(Direction direction) {
        return GLOBAL_STORAGE;

//        Objects.requireNonNull(direction);
//        return direction == Direction.DOWN ? GLOBAL_STORAGE : null;
    }

    public static Storage<ItemVariant> global() {
        return GLOBAL_STORAGE;
    }

    @Override
    protected Contents createSnapshot() {
        return this.contents.copy();
    }

    @Override
    protected void readSnapshot(Contents snapshot) {
        this.contents = snapshot;
    }

    @Override
    public long insert(ItemVariant resource, long amount, TransactionContext transaction) {
        StoragePreconditions.notBlankNotNegative(resource, amount);

        this.updateSnapshots(transaction);
        return this.contents.insert(resource, amount);
    }

    @Override
    public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notNegative(maxAmount);

        this.updateSnapshots(transaction);
        return this.contents.extract(resource, maxAmount);
    }

    @Override
    public Iterator<StorageView<ItemVariant>> iterator(TransactionContext transaction) {
        return new ConverterIterator<>(RandomizedIterator.from(this.contents.entries), variant -> exactView(transaction, variant));
    }

    @Override
    public StorageView<ItemVariant> exactView(TransactionContext transaction, ItemVariant resource) {
        return new CactusStorageView(this, resource);
    }

    public static class CactusStorageView implements StorageView<ItemVariant> {
        private final CactusStorage storage;
        private final ItemVariant variant;

        public CactusStorageView(CactusStorage storage, @Nullable ItemVariant variant) {
            this.storage = storage;
            this.variant = variant == null ? ItemVariant.blank() : variant;
        }

        @Override
        public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
            return this.storage.extract(resource, maxAmount, transaction);
        }

        @Override
        public boolean isResourceBlank() {
            return this.variant.isBlank();
        }

        @Override
        public ItemVariant getResource() {
            return this.variant;
        }

        @Override
        public long getAmount() {
            return this.storage.contents.get(this.variant);
        }

        @Override
        public long getCapacity() {
            return isResourceBlank() ? Integer.MAX_VALUE : Long.MAX_VALUE;
        }
    }

    public static class Contents {
        public static final String ITEM_KEY = "item";
        public static final String AMOUNT_KEY = "amount";
        @NotNull
        private final Object2LongOpenHashMap<ItemVariant> entries;

        public Contents() {
            this.entries = new Object2LongOpenHashMap<>();
        }

        public Contents(@NotNull Object2LongOpenHashMap<ItemVariant> entries) {
            this.entries = entries;
        }

        public static Contents deserialize(NbtList list) {
            Object2LongOpenHashMap<ItemVariant> entries = new Object2LongOpenHashMap<>(list.size());
            for (int i = 0; i < list.size(); i++) {
                NbtCompound nbt = list.getCompound(i);
                entries.put(ItemVariant.fromNbt(nbt.getCompound(ITEM_KEY)),
                        nbt.getLong(AMOUNT_KEY));
            }
            return new Contents(entries);
        }

        public NbtList serialize() {
            NbtList list = new NbtList();
            this.entries.forEach((variant, amount) -> {
                if (amount <= 0)
                    return;

                NbtCompound nbt = new NbtCompound();
                nbt.put(ITEM_KEY, variant.toNbt());
                nbt.put(AMOUNT_KEY, NbtLong.of(amount));
                list.add(nbt);
            });
            return list;
        }

        public Contents copy() {
            return new Contents(new Object2LongOpenHashMap<>(this.entries));
        }

        /**
         * @param variant Item key
         * @param amount  Amount to insert
         * @return Amount that was inserted
         */
        public long insert(ItemVariant variant, long amount) {
            long value = this.entries.getLong(variant);
            long space = Long.MAX_VALUE - value;
            if (space <= 0)
                return 0;
            if (space < amount)
                amount = space;
            this.entries.put(variant, value + amount);
            return amount;
        }

        /**
         * @param variant Item key
         * @param amount  Amount to extract
         * @return Amount that was extracted
         */
        public long extract(ItemVariant variant, long amount) {
            long value = this.entries.getLong(variant);
            if (value <= 0)
                return 0;
            if (value < amount)
                amount = value;
            this.entries.put(variant, value - amount);
            return amount;
        }

        /**
         * @param variant Item key
         * @return Amount of item in storage
         */
        public long get(ItemVariant variant) {
            return this.entries.getOrDefault(variant, 0L);
        }
    }
}
