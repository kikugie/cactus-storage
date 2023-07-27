package dev.kikugie.cactus_storage.util;

import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class InventoryUtil {
    public static final String BE_KEY = "BlockEntityTag";
    public static final String ITEMS_KEY = "Items";
    public static ItemStack cleanContents(ItemStack stack) {
        if (stack.getNbt() == null)
            return stack;

        if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof ShulkerBoxBlock) {
            NbtCompound nbt = stack.getOrCreateSubNbt(BE_KEY);
            nbt.remove(ITEMS_KEY);
            if (nbt.isEmpty())
                stack.getNbt().remove(BE_KEY);
        }

        if (stack.getItem() instanceof BundleItem) {
            NbtCompound nbt = stack.getOrCreateNbt();
            nbt.remove(ITEMS_KEY);
        }

        if (!stack.hasNbt())
            stack.setNbt(null);
        return stack;
    }
}
