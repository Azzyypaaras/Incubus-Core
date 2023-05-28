package net.id.incubus_core.misc;

import net.id.incubus_core.IncubusCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class IncubusDamageSources {

    public static final RegistryKey<DamageType> UNWORTHY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, IncubusCore.locate("unworthy"));
    public static final RegistryKey<DamageType> GRILLIN = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, IncubusCore.locate("grillin"));

    public static DamageSource grillin(Entity source) {
        return new DamageSource(source.getDamageSources().registry.getEntry(GRILLIN).orElseThrow(), source);
    }

    public static DamageSource unworthy(World world) {
        return new DamageSource(world.getDamageSources().registry.getEntry(UNWORTHY).orElseThrow());
    }

}
