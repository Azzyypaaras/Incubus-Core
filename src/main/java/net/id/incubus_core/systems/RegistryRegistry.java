package net.id.incubus_core.systems;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.id.incubus_core.IncubusCore;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public final class RegistryRegistry {

    public static void init() {}

    public static final RegistryKey<Registry<Material>> MATERIAL_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier(IncubusCore.MODID, "material"));
    public static final Registry<Material> MATERIAL = FabricRegistryBuilder.createSimple(MATERIAL_REGISTRY_KEY).buildAndRegister();
    // public static final Registry<Material> MATERIAL = (Registry<Material>) ((MutableRegistry) Registry.REGISTRIES).add(MATERIAL_REGISTRY_KEY, new SimpleRegistry<>(MATERIAL_REGISTRY_KEY, Lifecycle.experimental(), null), Lifecycle.experimental());

}
