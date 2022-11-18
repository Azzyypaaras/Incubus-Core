package net.id.incubus_core.mixin.player;

import net.id.incubus_core.misc.IncubusPlayerData;
import net.id.incubus_core.misc.item.LongSpatulaItem;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void parry(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.getAttacker() instanceof  PlayerEntity player) {
            var playerData = IncubusPlayerData.get(player);

            if (playerData.getParryStateTicks() > 0) {
                playerData.setParryStateTicks(0);

                LongSpatulaItem.applyParryEffects(player, PlayerEntity.class.cast(this));

                cir.cancel();
            }
        }
    }
}
