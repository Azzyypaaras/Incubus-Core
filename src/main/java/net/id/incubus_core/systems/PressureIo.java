package net.id.incubus_core.systems;

/**
 * An object that can hold and optionally take and provide kinetic energy.
 * Pressure energy is push-only.
 */
@SuppressWarnings("unused")
public interface PressureIo {

    /**
     * @return the amount of pressure being exerted.
     */
    long getPressure();

    /**
     * Pressurize this inventory, and return the amount of leftover pressure.
     *
     * <p>If simulation is {@link Simulation#SIMULATE}, the result of the operation must be returned, but the underlying state of the object must not change.
     *
     * @param amount     The amount of pressure to exert
     * @param simulation If {@link Simulation#SIMULATE}, do not mutate this object
     * @return the amount of pressure that could not be exerted
     */
    default long press(long amount, Simulation simulation) {
        return amount;
    }

    /**
     * Return false if this object does not support transfer at all, meaning that transfer will always return the passed amount,
     * and devices should not connect.
     */
    default boolean supportsTransfer() {
        return true;
    }
}
