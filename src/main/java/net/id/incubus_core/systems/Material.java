package net.id.incubus_core.systems;

import net.id.incubus_core.util.TagSuperset;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public record Material(double maxTemperature, double heatConductivity, double electricalConductivity, double resistance, long maxRads, long maxNm, double maxPressure, long maxFrequency, long maxInductance, TagSuperset<Item> components) {

    /**
     *
     * Notes:
     * Pixel width is a measure of the cross section of a material by how many pixels there are from edge to edge
     * Heat conductivity is divided by 100 to make it into a percentage, then divided again by 20 to account for tick timeframes
     *
     * @param maxTemperature The failure point of the material, in most cases this is the melting point.
     * @param heatConductivity The percentage of an object's stored heat that this material transfers each tick.
     * @param electricalConductivity The maximum amount of energy that this material can transfer each tick per pixel width.
     * @param resistance What percentage of energy is converted to heat (or generally lost) per block traveled.
     * @param maxRads The maximum rotational speed this material can tolerate before failure.
     * @param maxNm The maximum torque this material can tolerate before failure.
     * @param maxPressure The maximum pressure this material can tolerate before failure.
     * @param maxFrequency The maximum frequency of pulses this material can transfer without decoherence.
     * @param maxInductance The maximum inductance of pulses this material can tolerate before failure.
     * @param components Identifiers pointing to item tags that this material is associated with.
     */
    public Material(double maxTemperature, double heatConductivity, double electricalConductivity, double resistance, long maxRads, long maxNm, double maxPressure, long maxFrequency, long maxInductance, Identifier ... components) {
        this(maxTemperature, heatConductivity / 2000, electricalConductivity, resistance, maxRads, maxNm, maxPressure, maxFrequency, maxInductance, new TagSuperset<>(Registry.ITEM, components));
    }
}
