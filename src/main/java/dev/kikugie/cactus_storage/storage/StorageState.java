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
    private static StorageState instance;

    public static void setInstance(StorageState state) {
        instance = state;
    }

    public static void markStorageDirty() {
        if (instance != null)
            instance.markDirty();
    }

    public StorageState() {
        super();
    }

    public static StorageState fromNbt(NbtCompound tag, PersistentStateManager manager) {
        CactusStorage.load(tag);
        try {
            instance = getOrCreate(manager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    public static StorageState create(PersistentStateManager manager) {
        CactusStorage.create();
        try {
            instance = getOrCreate(manager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return instance;
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
