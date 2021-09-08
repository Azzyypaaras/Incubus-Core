package net.id.incubus_core.systems;

import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * An object that can hold and optionally take and provide heat energy.
 */
@SuppressWarnings("unused")
public interface HeatIo extends MaterialProvider {

    /**
     * Get the amount of heat stored.
     */
    double getTemperature();

    /**
     * Return false if this object does not support insertion at all, meaning that insertion will always return the passed amount,
     * and insert-only cables should not connect.
     */
    default boolean supportsTransfer() {
        return true;
    }

    /**
     * Insert heat into this inventory.
     *
     * @param amount The amount of heat to insert
     */
    void heat(double amount);

    /**
     * Remove heat from this inventory.
     *
     * @param amount The amount of heat to insert
     */
    void cool(double amount);

    /**
     * Get the preferred direction for ambient heat exchange, null for random.
     */
    @NotNull default Optional<Direction> getPreferredDirection() {
        return Optional.empty();
    }

    /**
     * Get the valid directions for ambient heat exchange.
     */
    @NotNull default List<Direction> getValidDirections() {
        return Collections.emptyList();
    }

    /**
     * Get the area available for heat exchange.
     * @param face the face of which the are is being requested.
     */
    default double getExchangeArea(@NotNull Direction face) {
        return 1;
    }

    /**
     * Should this object be able to exchange heat with the surrounding air?
     */
    default boolean allowAmbientHeatExchange() {
        return true;
    }
}
