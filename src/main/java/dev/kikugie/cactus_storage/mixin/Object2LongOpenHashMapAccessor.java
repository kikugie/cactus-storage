package dev.kikugie.cactus_storage.mixin;

import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Object2LongOpenHashMap.class, remap = false)
public interface Object2LongOpenHashMapAccessor {
    @Accessor("key")
    Object[] getKeys();
}
