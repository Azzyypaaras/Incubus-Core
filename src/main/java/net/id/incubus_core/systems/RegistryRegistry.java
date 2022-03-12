package net.id.incubus_core.systems;

import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.id.incubus_core.IncubusCore;
import com.mojang.serialization.Lifecycle;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;

public final class RegistryRegistry {

    public static void init() {}

    // TODO THIS MATERIAL REGISTRY WAS CHANGED FOR THE 1.18.2 UPDATE. MIGHT BE WRONG.
    // public static final RegistryKey<Registry<Material>> MATERIAL_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier(IncubusCore.MODID, "material"));
    public static final Registry<Material> MATERIAL = FabricRegistryBuilder.createSimple(Material.class, IncubusCore.id("material")).buildAndRegister();
    // public static final Registry<Material> MATERIAL = (Registry<Material>) ((MutableRegistry) Registry.REGISTRIES).add(MATERIAL_REGISTRY_KEY, new SimpleRegistry<>(MATERIAL_REGISTRY_KEY, Lifecycle.experimental(), null), Lifecycle.experimental());

}
