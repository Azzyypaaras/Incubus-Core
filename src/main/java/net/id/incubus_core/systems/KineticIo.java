package net.id.incubus_core.systems;

/**
 * An object that can hold and optionally take and provide kinetic energy.
 * Kinetic energy is pull-only.
 */
@SuppressWarnings("unused")
public interface KineticIo {

    /**
     * Get the total amount of kinetic energy stored.
     */
    default long getRotationalEnergy() {
        return getTorque() * getRotationalSpeed();
    }

    /**
     * @return the Newton-Meter (Nm) component of energy.
     */
    long getTorque();

    /**
     * @return the Radians per second (rad/s) component of energy.
     */
    long getRotationalSpeed();

    /**
     * Return false if this object does not support transfer at all, meaning that transfer will always return the passed amount,
     * and devices should not connect.
     */
    default boolean supportsTransfer() {
        return true;
    }
}
