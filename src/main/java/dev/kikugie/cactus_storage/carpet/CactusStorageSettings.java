package dev.kikugie.cactus_storage.carpet;

import carpet.settings.Rule;

public class CactusStorageSettings {
    public static final String CATEGORY = "cactus_storage";

    @Rule(category = CATEGORY, desc = "")
    public static boolean cactusStorage = false;

    @Rule(category = CATEGORY, desc = "")
    public static boolean allowCactusOnHoppers = false;

    @Rule(category = CATEGORY, desc = "")
    public static boolean dumpContentsOnPlayerBreak = false;
}
