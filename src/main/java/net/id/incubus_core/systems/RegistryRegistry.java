package net.id.incubus_core.systems;

import net.id.incubus_core.IncubusCoreCommon;
import com.mojang.serialization.Lifecycle;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;

@SuppressWarnings({"unchecked", "rawtypes"})
public final class RegistryRegistry {

    public static void init() {}

    public static final RegistryKey<Registry<Material>> MATERIAL_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier(IncubusCoreCommon.MODID, "material"));
    public static final Registry<Material> MATERIAL = (Registry<Material>) ((MutableRegistry) Registry.REGISTRIES).add(MATERIAL_REGISTRY_KEY, new SimpleRegistry<>(MATERIAL_REGISTRY_KEY, Lifecycle.experimental()), Lifecycle.experimental());


}
