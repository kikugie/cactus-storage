package dev.kikugie.cactus_storage.storage;

import dev.kikugie.cactus_storage.Reference;
import dev.kikugie.cactus_storage.mixin.PersistentStateManagerInvoker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

    public static StorageState getOrCreate(PersistentStateManager manager) throws IOException {
        File file = ((PersistentStateManagerInvoker) manager).file(Reference.MOD_ID);
        Path path = file.toPath();
        StorageState state = new StorageState();
        if (!Files.exists(path)) {
            Files.createFile(path);
            state.markDirty();
            state.save(file);
        }
        return state;
    }
}
