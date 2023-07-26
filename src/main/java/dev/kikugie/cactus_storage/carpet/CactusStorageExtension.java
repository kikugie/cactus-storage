package dev.kikugie.cactus_storage.carpet;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import dev.kikugie.cactus_storage.Reference;
import net.minecraft.server.MinecraftServer;

public class CactusStorageExtension implements CarpetExtension {
    public static final CactusStorageExtension INSTANCE = new CactusStorageExtension();
    public static final String name = Reference.MOD_ID.replace("_", "");

    @Override
    public String version() {
        return Reference.MOD_VERSION;
    }

    public static void init() {
        CarpetServer.manageExtension(INSTANCE);
    }

    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(CactusStorageSettings.class);
    }
}
