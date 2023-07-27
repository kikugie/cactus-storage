package dev.kikugie.cactus_storage;

import dev.kikugie.cactus_storage.carpet.CactusStorageExtension;
import dev.kikugie.cactus_storage.storage.CactusStorage;
import dev.kikugie.cactus_storage.storage.StorageState;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.Blocks;
import net.minecraft.world.PersistentStateManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CactusStorageMod implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        ItemStorage.SIDED.registerForBlocks((world, pos, state, blockEntity, direction) -> CactusStorage.get(direction), Blocks.CACTUS);
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            PersistentStateManager manager = server.getOverworld().getPersistentStateManager();
            manager.getOrCreate(
                    nbt -> StorageState.create(nbt, manager),
                    () -> StorageState.create(null, manager),
                    Reference.MOD_ID);
        });
        CactusStorageExtension.init();
    }
}
