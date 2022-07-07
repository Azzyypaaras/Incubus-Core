package net.id.incubus_core.dev.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.id.incubus_core.misc.CustomDeathMessageProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

import java.util.Optional;

/**
 * Destroyer of worlds.
 * <p>
 * Simple test thing for custom death messages.
 * <p>
 * Does unlimited damage.
 */
public final class EntityDeathMessageTestItem extends Item implements CustomDeathMessageProvider.EntityDamage {
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    
    public EntityDeathMessageTestItem(Settings settings) {
        super(settings);
        
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", Double.POSITIVE_INFINITY, EntityAttributeModifier.Operation.ADDITION));
        attributeModifiers = builder.build();
    }
    
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!stack.hasCustomName()) {
            stack.setCustomName(new LiteralText("Destroyer of Worlds"));
        }
    }
    
    @Override
    public Optional<Text> getDeathMessage(EntityDamageSource damageSource, LivingEntity target, ItemStack stack, EntityDamageType type) {
        return Optional.of(new TranslatableText("I hope this works out!"));
    }
    
    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.attributeModifiers;
        }
        return super.getAttributeModifiers(slot);
    }
}
