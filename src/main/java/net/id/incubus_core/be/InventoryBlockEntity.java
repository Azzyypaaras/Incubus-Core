package net.id.incubus_core.be;

import net.id.incubus_core.util.InventoryWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

/**
 * An abstract block entity that has an inventory, and a strategy for hoppers.
 * @see HopperStrategy
 */
@SuppressWarnings("unused")
public abstract class InventoryBlockEntity extends BlockEntity implements InventoryWrapper, SidedInventory {
    protected final DefaultedList<ItemStack> inventory;
    protected HopperStrategy hopperStrategy;

    public InventoryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int invSize, HopperStrategy hopperStrategy) {
        super(type, pos, state);
        inventory = DefaultedList.ofSize(invSize, ItemStack.EMPTY);
        this.hopperStrategy = hopperStrategy;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[inventory.size()];
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return switch (hopperStrategy) {
            case IN_TOP_OUT_BOTTOM -> dir == Direction.UP;
            case IN_ANY_OUT_BOTTOM -> dir != Direction.DOWN;
            case IN_ANY, ALL_PASS -> true;
            default -> false;
        };
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return switch (hopperStrategy) {
            case IN_TOP_OUT_BOTTOM, IN_ANY_OUT_BOTTOM -> dir == Direction.DOWN;
            case OUT_ANY, ALL_PASS -> true;
            default -> false;
        };
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    /**
     * A strategy for how to deal with hoppers and the items that they may want to pass
     */
    public enum HopperStrategy {
        /**
         * Hopper items can enter through the top and leave through the bottom
         */
        IN_TOP_OUT_BOTTOM,
        /**
         * Hopper items can enter from any side and leave through the bottom
         */
        IN_ANY_OUT_BOTTOM,
        /**
         * Hopper items can enter from any side, but cannot leave
         */
        IN_ANY,
        /**
         * Hopper items can leave from any side, but cannot enter
         */
        OUT_ANY,
        /**
         * Hopper items can enter and leave from any side
         */
        ALL_PASS,
        /**
         * Hopper items cannot enter or leave this from any side
         */
        NO_PASS
    }
}
