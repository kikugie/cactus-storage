package dev.kikugie.cactus_storage.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.kikugie.cactus_storage.carpet.CactusStorageSettings;
import dev.kikugie.cactus_storage.storage.CactusStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.Optional;

@Mixin(Block.class)
public class BlockMixin {
    @ModifyExpressionValue(
            method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;",
            at = @At(
                    value = "INVOKE",
                    //#if MC < 12000
                    target = "Lnet/minecraft/block/BlockState;getDroppedStacks(Lnet/minecraft/loot/context/LootContext$Builder;)Ljava/util/List;"
                    //#else
                    //$$ target = "Lnet/minecraft/block/BlockState;getDroppedStacks(Lnet/minecraft/loot/context/LootContextParameterSet$Builder;)Ljava/util/List;"
                    //#endif
            ))
    private static List<ItemStack> cactus_storage$yeetCactusContents(
            List<ItemStack> original,
            @Local BlockState state,
            @Local @Nullable Entity entity,
            @Local ItemStack tool) {
        if (!state.isOf(Blocks.CACTUS))
            return original;

        if (!CactusStorageSettings.alwaysDropCactusContents &&
                (!CactusStorageSettings.dropCactusContents
                        || !(entity instanceof PlayerEntity)
                        || EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) != 0))
            return original;
        return cactus_storage$processItems(original);
    }

    @ModifyExpressionValue(
            method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;)Ljava/util/List;",
            at = @At(
                    value = "INVOKE",
                    //#if MC < 12000
                    target = "Lnet/minecraft/block/BlockState;getDroppedStacks(Lnet/minecraft/loot/context/LootContext$Builder;)Ljava/util/List;"
                    //#else
                    //$$ target = "Lnet/minecraft/block/BlockState;getDroppedStacks(Lnet/minecraft/loot/context/LootContextParameterSet$Builder;)Ljava/util/List;"
                    //#endif
            ))
    private static List<ItemStack> cactus_storage$megaYeetCactusContents(
            List<ItemStack> original) {
        return CactusStorageSettings.alwaysDropCactusContents ? cactus_storage$processItems(original) : original;
    }

    @Unique
    private static List<ItemStack> cactus_storage$processItems(List<ItemStack> original) {
        Optional<CactusStorage> storage = CactusStorage.getInstance();
        if (storage.isEmpty())
            return original;

        List<ItemStack> cactusContents = storage.get().clear();
        cactusContents.addAll(original);
        return cactusContents;
    }

}
