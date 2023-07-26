package dev.kikugie.cactus_storage.mixin;

import dev.kikugie.cactus_storage.carpet.CactusStorageSettings;
import dev.kikugie.cactus_storage.storage.CactusStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getStack();

    /**
     * If entity is killed by a cactus, inserts it into the cactus storage. This bypasses {@code supportsInsertion} check, and considered the only valid method of inserting items.
     *
     * @param source Source of damage. Must be cactus for operation to succeed.
     * @param amount Damage amount. Not used.
     * @param cir Mixin callback info. Not used.
     */
    @SuppressWarnings("UnstableApiUsage")
    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;remove()V"))
    private void cactus_storage$addToCactusStorage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!CactusStorageSettings.cactusStorage
                || source != DamageSource.CACTUS
                || this.getEntityWorld() instanceof ServerWorld)
            return;

        try (Transaction transaction = Transaction.openOuter()) {
            ItemStack stack = getStack();
            CactusStorage.get().insert(ItemVariant.of(stack), stack.getCount(), transaction);
            transaction.commit();
        }
    }
}
