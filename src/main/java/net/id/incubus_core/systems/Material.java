package net.id.incubus_core.systems;

import net.id.incubus_core.util.TagSuperset;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;

public class Material {

    public final TagSuperset<Item> tags;
    public final double maxTemperature, heatConductivity;
    public final double electricalConductivity, resistance;
    public final long maxRads, maxNm;
    public final double maxPressure;

    public Material(double maxTemperature, double heatConductivity, double electricalConductivity, double resistance, long maxRads, long maxNm, double maxPressure, Identifier ... components) {
        this.tags = new TagSuperset<>(DefaultedRegistry.ITEM_KEY, components);
        this.maxTemperature = maxTemperature;
        this.heatConductivity = heatConductivity / 100;
        this.electricalConductivity = electricalConductivity / 100;
        this.resistance = resistance / 100;
        this.maxRads = maxRads;
        this.maxNm = maxNm;
        this.maxPressure = maxPressure;
    }
}
