package dev.kikugie.cactus_storage.storage;

import dev.kikugie.cactus_storage.Reference;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;

public class StorageState extends PersistentState {
    public StorageState() {
        super(Reference.MOD_ID);
    }

    @Override
    public void fromTag(NbtCompound tag) {
        CactusStorage.load(tag);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        return CactusStorage.save(nbt);
    }
}
