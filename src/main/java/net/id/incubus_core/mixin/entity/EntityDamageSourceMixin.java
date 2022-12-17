package net.id.incubus_core.mixin.entity;

import java.util.Optional;
import net.id.incubus_core.misc.CustomDeathMessageProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Current mixin features:
 * - Hooks death message for easy item-specific messages
 *
 * @author gudenau
 */
@Mixin(EntityDamageSource.class)
public abstract class EntityDamageSourceMixin extends DamageSource {
    /**
     * Never called.
     *
     * @hidden
     */
    private EntityDamageSourceMixin() {
        super(null);
    }
    
    /**
     * Hook for the custom death message.
     *
     * @param entity The killed entity
     * @param cir    Mixin callback information
     * @param stack  The item stack used to attack
     */
    @Inject(
        method = "getDeathMessage",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/entity/damage/EntityDamageSource;name:Ljava/lang/String;"
        ),
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void getDeathMessage(LivingEntity entity, CallbackInfoReturnable<Text> cir, ItemStack stack) {
        if (stack.getItem() instanceof CustomDeathMessageProvider.EntityDamage customItem) {
            var customText = customItem.getDeathMessage(
                (EntityDamageSource) (Object) this,
                entity,
                new CustomDeathMessageProvider.EntityDamage.Data(
                    stack,
                    switch (name) {
                        case "sting" -> CustomDeathMessageProvider.EntityDamageType.STING;
                        case "mob" -> CustomDeathMessageProvider.EntityDamageType.MOB;
                        case "player" -> CustomDeathMessageProvider.EntityDamageType.PLAYER;
                        case "thorns" -> CustomDeathMessageProvider.EntityDamageType.THORNS;
                        case "explosion.player" -> CustomDeathMessageProvider.EntityDamageType.PLAYER_EXPLOSION;
                        default -> CustomDeathMessageProvider.EntityDamageType.OTHER;
                    }
                )
            );
            customText.ifPresent(cir::setReturnValue);
        }
    }
}
