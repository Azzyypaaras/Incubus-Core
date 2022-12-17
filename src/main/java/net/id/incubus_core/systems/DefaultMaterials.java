package net.id.incubus_core.systems;

import net.id.incubus_core.IncubusCore;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class DefaultMaterials {

    public static void init() {}

    //public static final Material MYRCIL = register("myrcil", new Material(475, 0.03, 0, 0, 27500));
    public static final Material AIR = register("air", new Material(15000000, 0.25, 1, 50, 0, 0, 0, 0, 0));
    public static final Material IRON = register("iron", new Material(900, 9.5, 96, 0.25, 2048, 512, 85000, 512, 1024, new Identifier("c", "iron_ingots")));
    public static final Material COPPER = register("copper", new Material(550, 21.5, 256, 0.1, 256, 128, 22500, 2048, 512, new Identifier("c", "copper_ingots")));
    public static final Material GOLD = register("gold", new Material(500, 15, 196, 0.1, 256, 64, 15000, 32768, 1028, new Identifier("c", "gold_ingots")));
    public static final Material SILVER = register("silver", new Material(450, 17.5, 1024, 0.015, 128, 256, 31000, 8192, 4096, new Identifier("c", "silver_ingots")));
    public static final Material DIAMOND = register("diamond", new Material(175, 42.75, 16, 0.05, 16384, 128, 137000, 65536, 64, new Identifier("c", "diamonds")));
    public static final Material AMETHYST = register("amethyst", new Material(950, 13.5, 24, 0.25, 4096, 256, 47000, 32, 32768, new Identifier("c", "amethyst")));
    public static final Material QUARTZ = register("quartz", new Material(1500, 12, 128, 0.5, 4096, 256, 47000, 32, 32768, new Identifier("c", "quartz")));
    public static final Material FLESH = register("flesh", new Material(100, 31.25, 2.125, 1, 1024, 32, 8500, 32768, 2, new Identifier("c", "raw_meat")));
    //public static final Material HSLA_STEEL = register("hsla_steel", new Material(1600, 0.25, 1024, 2048, 150000));
    //public static final Material UNOBTANIUM = register("unobtanium", new Material(Double.POSITIVE_INFINITY, 1, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));

    private static Material register(String id, Material material) {
        return Registry.register(RegistryRegistry.MATERIAL, IncubusCore.locate(id), material);
    }
}
