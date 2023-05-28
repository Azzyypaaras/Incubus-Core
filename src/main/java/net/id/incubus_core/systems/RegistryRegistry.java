package net.id.incubus_core.systems;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.id.incubus_core.IncubusCore;
import net.minecraft.registry.Registry;

public final class RegistryRegistry {

    public static void init() {}

    // TODO THIS MATERIAL REGISTRY WAS CHANGED FOR THE 1.18.2 UPDATE. MIGHT BE WRONG.
    // TODO - Materials were REMOVED for 1.20, this needs to go
    // public static final RegistryKey<Registry<Material>> MATERIAL_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier(IncubusCore.MODID, "material"));
    public static final Registry<Material> MATERIAL = FabricRegistryBuilder.createSimple(Material.class, IncubusCore.locate("material")).buildAndRegister();
    // public static final Registry<Material> MATERIAL = (Registry<Material>) ((MutableRegistry) Registry.REGISTRIES).add(MATERIAL_REGISTRY_KEY, new SimpleRegistry<>(MATERIAL_REGISTRY_KEY, Lifecycle.experimental(), null), Lifecycle.experimental());

}
