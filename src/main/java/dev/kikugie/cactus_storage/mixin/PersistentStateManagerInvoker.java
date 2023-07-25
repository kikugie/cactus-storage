package dev.kikugie.cactus_storage.mixin;

import net.minecraft.world.PersistentStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.File;

@Mixin(PersistentStateManager.class)
public interface PersistentStateManagerInvoker {
    @Invoker("getFile")
    File file(String id);
}
