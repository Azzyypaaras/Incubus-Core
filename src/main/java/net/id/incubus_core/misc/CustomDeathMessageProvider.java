package net.id.incubus_core.misc;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Allows an easy way to provide custom death messages without custom {@link DamageSource}s.
 *
 * @since 1.7.0
 */
public interface CustomDeathMessageProvider<S extends DamageSource, D> {
    /**
     * Gets a custom death message for a damage source.
     *
     * @param damageSource The actual damage source
     * @param target       The target of the damage
     * @param data         Context specific data
     * @return The text to use, or empty for default
     */
    Optional<Text> getDeathMessage(S damageSource, LivingEntity target, D data);
    
    /**
     * A damage type specific to {@link EntityDamageSource}.
     */
    interface EntityDamage extends CustomDeathMessageProvider<EntityDamageSource, EntityDamage.Data> {
        @Override
        default Optional<Text> getDeathMessage(EntityDamageSource damageSource, LivingEntity target, Data data) {
            return getDeathMessage(damageSource, target, data.stack(), data.type());
        }
        
        /**
         * Gets a custom death message for a damage source.
         *
         * @param damageSource The actual damage source
         * @param target       The target of the damage
         * @param stack        The {@link ItemStack} that caused the damage
         * @param type         The type of entity damage
         * @return The text to use, or empty for default
         */
        Optional<Text> getDeathMessage(EntityDamageSource damageSource, LivingEntity target, ItemStack stack, EntityDamageType type);
        
        record Data(
            @NotNull ItemStack stack,
            @NotNull EntityDamageType type
        ) {
        }
    }
    
    enum EntityDamageType {
        /**
         * Caused by a mob.
         */
        MOB,
        /**
         * Caused directly by a player.
         */
        PLAYER,
        /**
         * Caused by explosions the player created.
         */
        PLAYER_EXPLOSION,
        /**
         * Caused by bees.
         */
        STING,
        /**
         * Caused by the thorns enchantment.
         */
        THORNS,
        /**
         * Unknown damage type.
         * <p>
         * Should remain the last enum value.
         */
        OTHER,
    }
}
