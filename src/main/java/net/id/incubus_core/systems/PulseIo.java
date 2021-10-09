package net.id.incubus_core.systems;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

/**
 * An object that can take, provide, and optionally store Pulses.
 * A pulse is composed of three parts:
 * Motive (The energy of the pulse, which in of itself has two components, Frequency and Inductance)
 * Polarity (Neutral, Positive, Negative, or None, these cannot freely combine. None is the default).
 * Dissonance (A count of how many generators are connected, different generators add different amounts, most machines should fail if it goes over 50)
 */
@SuppressWarnings("unused")
public interface PulseIo extends MaterialProvider {

    /**
     * Get the total amount of energy stored
     */
    default long getMotive() {
        return getFrequency() * getInductance();
    }

    /**
     * Get the frequency component of the stored energy
     */
    long getFrequency();

    /**
     * Get the inductance component of the stored energy
     */
    long getInductance();

    /**
     * Get the polarity of the stored energy
     */
    @NotNull default Polarity getPolarity() {
        return Polarity.NONE;
    }

    /**
     * Get the dissonance of the stored energy
     */
    double getDissonance();

    /**
     * Get the maximum amount of energy that can be stored, or 0 if unsupported or unknown.
     */
    default long getMotiveCapacity() {
        return 0;
    }

    /**
     * Get the minimum level of Inductance needed to cause this object to fail.
     */
    default long getFailureInductance() {
        return getMaterial(null).maxInductance();
    }

    /**
     * Get the minimum frequency needed to cause this object to fail.
     */
    default long getFailureFrequency() {
        return getMaterial(null).maxFrequency();
    }

    /**
     * Get the maximum amount of dissonance this object can tolerate.
     */
    default long getFailureDissonance() {
        return 50;
    }

    /**
     * Get the io category of the object
     */
    @NotNull default Category getDeviceCategory() {
        return Category.UNKNOWN;
    }

    /**
     * Get the io capabilities for the respective face
     */
    @NotNull default IoType getIoCapabilities(Direction direction) {
        return IoType.NONE;
    }

    /**
     * Insert energy into this inventory, and return the amount of leftover energy.
     *
     * <p>If simulation is {@link Simulation#SIMULATE}, the result of the operation must be returned, but the underlying state of the object must not change.
     *
     * @param amount     The amount of energy to insert
     * @param simulation If {@link Simulation#SIMULATE}, do not mutate this object
     * @return the amount of energy that could not be inserted
     */
    default long transferFrequency(long amount, Simulation simulation) {
        return amount;
    }

    /**
     * Insert energy into this inventory, and return the amount of leftover energy.
     *
     * <p>If simulation is {@link Simulation#SIMULATE}, the result of the operation must be returned, but the underlying state of the object must not change.
     *
     * @param amount     The amount of energy to insert
     * @param simulation If {@link Simulation#SIMULATE}, do not mutate this object
     * @return the amount of energy that could not be inserted
     */
    default long transferInductance(long amount, Simulation simulation) {
        return amount;
    }

    /**
     * Insert energy into this inventory, and return the amount of leftover energy (meant primarily for items and capacitors).
     *
     * <p>If simulation is {@link Simulation#SIMULATE}, the result of the operation must be returned, but the underlying state of the object must not change.
     *
     * @param amount     The amount of energy to insert
     * @param simulation If {@link Simulation#SIMULATE}, do not mutate this object
     * @return the amount of energy that could not be inserted
     */
    default long insertMotive(long amount, Simulation simulation) {
        return amount;
    }

    /**
     * Extract energy from this inventory, and return the amount of energy that was extracted (meant primarily for items and capacitors).
     *
     * <p>If simulation is {@link Simulation#SIMULATE}, the result of the operation must be returned, but the underlying state of the object must not change.
     *
     * @param maxAmount     The maximum amount of energy to extract
     * @param simulation 	If {@link Simulation#SIMULATE}, do not mutate this object
     * @return the amount of energy that was extracted
     */
    default long extractMotive(long maxAmount, Simulation simulation) {
        return 0;
    }

    /**
     * What do you want me to explain here? Half the bloody methods are already just copy pasted!
     */
    void setPolarity(Polarity polarity);

    /**
     * Return false if this object does not support insertion at all, meaning that insertion will always return the passed amount,
     * and insert-only connectors should not connect.
     */
    default boolean supportsInsertion() {
        return true;
    }

    /**
     * Return false if this object does not support extraction at all, meaning that extraction will always return 0,
     * and extract-only connectors should not connect.
     */
    default boolean supportsExtraction() {
        return false;
    }

    /**
     * Indicates what the object does with relation to pulses.
     */
    enum Category {
        PRODUCER,
        CONSUMER,
        CONNECTOR,
        MODULATOR,
        CAPACITOR,
        BATTERY,
        UNKNOWN
    }

    /**
     * Indicates what a face of the object is able to do with pulses
     */
    enum IoType implements StringIdentifiable {
        INPUT,
        OUTPUT,
        NONE;

        @Override
        public String asString() {
            return name().toLowerCase();
        }
    }
}
