package net.id.incubus_core.misc.item;

import net.minecraft.entity.effect.*;
import net.minecraft.item.*;

public class IncubusFoodComponents {

    public static final FoodComponent BERRY_BRANCH = new FoodComponent.Builder()
            .hunger(2).saturationModifier(0.6F).snack()
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1, false, false, true), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 0, false, false, true), 0.1F)
            .build();

    public static final FoodComponent MOBILK_1 = new FoodComponent.Builder()
            .alwaysEdible().hunger(200).saturationModifier(10F)
            .statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 1200, 0), 1)
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 1200, 0), 1)
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 1200, 0), 1)
            .statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 1200, 0), 1)
            .statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1200, 0), 1)
            .build();

    public static final FoodComponent LEAN = new FoodComponent.Builder()
            .alwaysEdible().hunger(1).saturationModifier(1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 600, 0), 1)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 159), 1)
            .statusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 600, 0), 1)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 1200, 0), 1)
            .build();

    public static final FoodComponent RAT_POISON = new FoodComponent.Builder()
            .alwaysEdible().hunger(1).saturationModifier(0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1200, 1), 1)
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 1200, 0), 1)
            .build();
}
