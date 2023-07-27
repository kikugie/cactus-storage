package dev.kikugie.cactus_storage.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.kikugie.cactus_storage.carpet.CactusStorageSettings;
import dev.kikugie.cactus_storage.storage.CactusStorage;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.Hopper;
import net.minecraft.entity.vehicle.HopperMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HopperMinecartEntity.class)
public class HopperMinecartEntityMixin {
    /**
     * If hopper minecart's inventory extraction fails, tries to extract from the cactus storage. It searches for cactus 1 and 2 blocks above minecart's block position. If this extraction fails, it will let vanilla code to try pulling item entities.
     *
     * @param hopper Hopper of the hopper minecart entity.
     * @param original Original inventory extraction operation.
     * @return True if extraction was successful.
     */
    @SuppressWarnings("UnstableApiUsage")
    @WrapOperation(method = "canOperate", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;extract(Lnet/minecraft/world/World;Lnet/minecraft/block/entity/Hopper;)Z", ordinal = 0))
    private boolean cactus_storage$pullFromCactusStorage(World world, Hopper hopper, Operation<Boolean> original) {
        boolean result = original.call(world, hopper);
        if (result)
            return true;

        BlockPos invPos = new BlockPos(hopper.getHopperX(), Math.ceil(hopper.getHopperY()), hopper.getHopperZ());
        BlockState target = world.getBlockState(invPos);
        BlockState target2 = world.getBlockState(invPos.offset(Direction.UP));

        if (!CactusStorageSettings.cactusStorage
                || !(target2.isOf(Blocks.CACTUS)
                || target.isOf(Blocks.CACTUS)))
            return false;

        return StorageUtil.move(CactusStorage.get(),
                InventoryStorage.of(hopper, Direction.UP),
                iv -> true,
                1,
                null) == 1;
    }
}
