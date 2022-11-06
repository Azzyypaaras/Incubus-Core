package net.id.incubus_core.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class IncubusDamageSources {

    public static final DamageSource UNWORTHY = new UnworthyDamageSource("ic_unworthy");

    public static DamageSource grillin(Entity source) {
        return new GrillinDamageSource("ic_grillin", source);
    }


    private static class UnworthyDamageSource extends DamageSource {


        protected UnworthyDamageSource(String name) {
            super(name);
        }

        @Override
        public boolean bypassesArmor() {
            return true;
        }

        @Override
        public boolean isOutOfWorld() {
            return true;
        }

        @Override
        public boolean isNeutral() {
            return true;
        }

        @Override
        public boolean isUnblockable() {
            return true;
        }
    }

    private static class GrillinDamageSource extends EntityDamageSource {

        public GrillinDamageSource(String name, Entity source) {
            super(name, source);
        }

        @Override
        public boolean bypassesArmor() {
            return true;
        }

        @Override
        public boolean isUnblockable() {
            return true;
        }
    }
}
