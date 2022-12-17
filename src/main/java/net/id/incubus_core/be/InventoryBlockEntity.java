package net.id.incubus_core.be;

import net.id.incubus_core.util.InventoryWrapper;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

import static com.google.common.base.Predicates.alwaysFalse;
import static com.google.common.base.Predicates.alwaysTrue;

/**
 * A block entity that has an inventory and a strategy for hoppers.
 * @see HopperStrategy
 */
@SuppressWarnings("unused")
public interface InventoryBlockEntity extends InventoryWrapper, SidedInventory {

    @Override
    default int[] getAvailableSlots(Direction side) {
        return new int[this.getItems().size()];
    }

    @NotNull
    HopperStrategy getHopperStrategy();

    @Override
    default boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.getHopperStrategy().canInsert(dir);
    }

    @Override
    default boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return this.getHopperStrategy().canExtract(dir);
    }

    /**
     * A strategy for how to deal with hoppers and the items that they may want to pass
     */
    @SuppressWarnings("Guava")
    enum HopperStrategy {
        /**
         * Hopper items can enter through the top and leave through the bottom
         */
        IN_TOP_OUT_BOTTOM(dir -> dir == Direction.UP, dir -> dir == Direction.DOWN),
        /**
         * Hopper items can enter from any side and leave through the bottom
         */
        IN_ANY_OUT_BOTTOM(alwaysTrue(), dir -> dir == Direction.DOWN),
        /**
         * Hopper items can enter from any side, but cannot leave
         */
        IN_ANY(alwaysTrue(), alwaysFalse()),
        /**
         * Hopper items can leave from any side, but cannot enter
         */
        OUT_ANY(alwaysFalse(), alwaysTrue()),
        /**
         * Hopper items can enter and leave from any side
         */
        ALL_PASS(alwaysTrue(), alwaysTrue()),
        /**
         * Hopper items cannot enter or leave this from any side
         */
        NO_PASS(alwaysFalse(), alwaysFalse());

        private final Predicate<@Nullable Direction> canInsert;
        private final Predicate<Direction> canExtract;

        HopperStrategy(Predicate<@Nullable Direction> canInsert, Predicate<Direction> canExtract) {
            this.canInsert = canInsert;
            this.canExtract = canExtract;
        }

        /**
         * @return Whether this strategy can receive items from the given direction
         */
        public final boolean canInsert(@Nullable Direction dir) {
            return this.canInsert.test(dir);
        }

        /**
         * @return Whether this strategy can extract items from the given direction
         */
        public final boolean canExtract(Direction dir) {
            return this.canExtract.test(dir);
        }
    }
}
