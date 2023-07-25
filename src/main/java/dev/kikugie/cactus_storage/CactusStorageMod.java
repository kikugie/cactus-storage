package dev.kikugie.cactus_storage;

import dev.kikugie.cactus_storage.storage.CactusStorage;
import dev.kikugie.cactus_storage.storage.StorageState;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.Blocks;

import java.util.logging.Logger;

public class CactusStorageMod implements ModInitializer {
    public static final Logger LOGGER = Logger.getLogger("cactus_storage");

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        ItemStorage.SIDED.registerForBlocks((world, pos, state, blockEntity, direction) -> CactusStorage.get(direction), Blocks.CACTUS);
        ServerLifecycleEvents.SERVER_STARTED.register(server -> server.getOverworld().getPersistentStateManager().set(new StorageState()));
    }
}
