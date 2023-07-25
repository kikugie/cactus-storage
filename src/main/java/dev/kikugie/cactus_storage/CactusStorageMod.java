package dev.kikugie.cactus_storage;

import dev.kikugie.cactus_storage.storage.CactusStorage;
import dev.kikugie.cactus_storage.storage.StorageState;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.Blocks;
import net.minecraft.world.PersistentStateManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class CactusStorageMod implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);
    private static StorageState storageState;

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        ItemStorage.SIDED.registerForBlocks((world, pos, state, blockEntity, direction) -> CactusStorage.get(direction), Blocks.CACTUS);
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            PersistentStateManager manager = server.getOverworld().getPersistentStateManager();
            storageState = manager.getOrCreate(() -> {
                try {
                    return StorageState.getOrCreate(manager);
                } catch (IOException e) {
                    LOGGER.error("Cactus storage data is corrupted! Fix it or delete the file in `<world>/data/cactus_storage.dat`.");
                    throw new RuntimeException(e);
                }
            }, Reference.MOD_ID);
        });
    }

    public static void markStorageDirty() {
        if (storageState != null)
            storageState.markDirty();
    }
}
