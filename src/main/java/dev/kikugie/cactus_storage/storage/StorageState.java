package dev.kikugie.cactus_storage.storage;

import dev.kikugie.cactus_storage.CactusStorageMod;
import dev.kikugie.cactus_storage.Reference;
import dev.kikugie.cactus_storage.mixin.PersistentStateManagerInvoker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StorageState extends PersistentState {
    public StorageState() {
        super();
    }

    public static StorageState create(@Nullable NbtCompound nbt, PersistentStateManager manager) {
        StorageState saveManager = new StorageState();
        CactusStorage.load(nbt, saveManager);
        createFile(manager);
        return saveManager;
    }

    private static void createFile(PersistentStateManager manager) {
        File file = ((PersistentStateManagerInvoker) manager).file(Reference.MOD_ID);
        Path path = file.toPath();
        if (!Files.exists(path))
            try {
                Files.createFile(path);
            } catch (IOException e) {
                CactusStorageMod.LOGGER.error("Failed to create cactus storage file", e);
                throw new RuntimeException(e);
            }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        return CactusStorage.save(nbt);
    }
}
