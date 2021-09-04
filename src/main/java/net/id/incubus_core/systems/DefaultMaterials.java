package net.id.incubus_core.systems;

import net.id.incubus_core.IncubusCoreCommon;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class DefaultMaterials {

    public static void init() {}

    //public static final Material MYRCIL = register("myrcil", new Material(475, 0.03, 0, 0, 27500));
    public static final Material IRON = register("iron", new Material(1250, 9.5, 5.75, 8.5, 2048, 512, 85000, new Identifier("c", "iron_ingots")));
    public static final Material COPPER = register("copper", new Material(700, 21.5, 17.5, 5.5, 256, 128, 15000, new Identifier("c", "copper_ingots")));
    public static final Material GOLD = register("gold", new Material(600, 15, 13, 1.5, 256, 64, 22500, new Identifier("c", "gold_ingots")));
    public static final Material DIAMOND = register("diamond", new Material(250, 52.75, 35, 2.5, 16384, 128, 137000, new Identifier("c", "diamonds")));
    public static final Material AMETHYST = register("amethyst", new Material(850, 13.5, 36.75, 9.5, 4096, 256, 47000, new Identifier("c", "amethyst")));
    //public static final Material HSLA_STEEL = register("hsla_steel", new Material(1600, 0.25, 1024, 2048, 150000));
    //public static final Material UNOBTANIUM = register("unobtanium", new Material(Double.POSITIVE_INFINITY, 1, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));

    private static Material register(String id, Material material) {
        return Registry.register(RegistryRegistry.MATERIAL, new Identifier(IncubusCoreCommon.MODID), material);
    }
}
