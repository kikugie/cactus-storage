package dev.kikugie.cactus_storage.carpet;

import carpet.settings.Rule;
import dev.kikugie.cactus_storage.Reference;

public class CactusStorageSettings {
    public static final String CATEGORY = Reference.MOD_ID;

    @Rule(category = CATEGORY, desc = """
            Stores items killed by cacti in a global storage.
            Items can be retrieved using hoppers or hopper minecarts.
            For implementation details read mod page.
            """)
    public static boolean cactusStorage = false;

    @Rule(category = CATEGORY, desc = """
            Allows cacti to be placed on top of hoppers.
            Makes extracting items from cactus storage easier.
            """)
    public static boolean allowCactusOnHoppers = false;

    @Rule(category = CATEGORY, desc = """
            Drop ALL cactus storage contents when a cactus is broken by a player without silk touch.
            Doesn't affect creative mode.""")
    public static boolean dropCactusContents = false;
}
