package azzy.fabric.incubus_core.be;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public interface FalseInventory extends Inventory {

    @Override
    default boolean isEmpty() {
        return false;
    }

    @Override
    default ItemStack getStack(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    default ItemStack removeStack(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    default ItemStack removeStack(int slot) {
        return removeStack(slot, 1);
    }

    @Override
    default void setStack(int slot, ItemStack stack) {
    }

    @Override
    default void markDirty() {
    }

    @Override
    default boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    default void clear() {
    }

    @Override
    default int size() {
        return Integer.MAX_VALUE;
    }
}
