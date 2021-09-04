package net.id.incubus_core.mixin;

import net.id.incubus_core.misc.PlayerChecker;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.id.incubus_core.misc.PlayerChecker.*;

@Mixin(PlayerEntity.class)
public abstract class NameDisplayMixin {

    @Shadow public abstract GameProfile getGameProfile();

    @Inject(method = "getName", at = @At("HEAD"), cancellable = true)
    public void textNameIntercept(CallbackInfoReturnable<Text> cir) {
        GameProfile profile = getGameProfile();
        if(profile.getId().equals(PlayerChecker.WORTHY_PLAYERS.get(AZZY))) {
            cir.setReturnValue(new LiteralText("§bLunarian Emperor | " + profile.getName() + "§r"));
            cir.cancel();
        }
        else if(profile.getId().equals(PlayerChecker.WORTHY_PLAYERS.get(PIE))) {
            cir.setReturnValue(new LiteralText("ULTRASHILL | " + profile.getName()));
            cir.cancel();
        }
        else if(profile.getId().equals(PlayerChecker.WORTHY_PLAYERS.get(JER))) {
            cir.setReturnValue(new LiteralText("Lord of brain rot | " + profile.getName()));
            cir.cancel();
        }
    }

    @Inject(method = "getEntityName", at = @At("HEAD"), cancellable = true)
    public void stringNameIntercept(CallbackInfoReturnable<String> cir) {
        GameProfile profile = getGameProfile();
        if(profile.getId().equals(PlayerChecker.WORTHY_PLAYERS.get(AZZY))) {
            cir.setReturnValue("§bThe last lunarian emperor | " + profile.getName() + "§r");
            cir.cancel();
        }
        else if(profile.getId().equals(PlayerChecker.WORTHY_PLAYERS.get(PIE))) {
            cir.setReturnValue("ULTRASHILL | " + profile.getName());
            cir.cancel();
        }
        else if(profile.getId().equals(PlayerChecker.WORTHY_PLAYERS.get(JER))) {
            cir.setReturnValue("Heir to the cum throne | " + profile.getName());
            cir.cancel();
        }
    }
}
