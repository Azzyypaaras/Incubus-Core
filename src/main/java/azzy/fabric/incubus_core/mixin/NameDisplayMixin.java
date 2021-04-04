package azzy.fabric.incubus_core.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class NameDisplayMixin {

    @Shadow public abstract GameProfile getGameProfile();

    @Inject(method = "getName", at = @At("HEAD"), cancellable = true)
    public void textNameIntercept(CallbackInfoReturnable<Text> cir) {
        GameProfile profile = getGameProfile();
        if(profile.getId().equals(UUID.fromString("f7957087-549e-4ca3-878e-48f36569dd3e"))) {
            cir.setReturnValue(new LiteralText("§bThe last lunarian emperor | " + profile.getName() + "§r"));
            cir.cancel();
        }
        else if(profile.getId().equals(UUID.fromString("a250dea2-a0ec-4aa4-bfa9-858a44466241"))) {
            cir.setReturnValue(new LiteralText("ULTRASHILL | " + profile.getName()));
            cir.cancel();
        }
        else if(profile.getId().equals(UUID.fromString("0461feb0-c0a6-4020-a612-3d24a4ff3f3b"))) {
            cir.setReturnValue(new LiteralText("Heir to the cum throne | " + profile.getName()));
            cir.cancel();
        }
    }

    @Inject(method = "getEntityName", at = @At("HEAD"), cancellable = true)
    public void stringNameIntercept(CallbackInfoReturnable<String> cir) {
        GameProfile profile = getGameProfile();
        if(profile.getId().equals(UUID.fromString("f7957087-549e-4ca3-878e-48f36569dd3e"))) {
            cir.setReturnValue("§bThe last lunarian emperor | " + profile.getName() + "§r");
            cir.cancel();
        }
        else if(profile.getId().equals(UUID.fromString("a250dea2-a0ec-4aa4-bfa9-858a44466241"))) {
            cir.setReturnValue("ULTRASHILL | " + profile.getName());
            cir.cancel();
        }
        else if(profile.getId().equals(UUID.fromString("0461feb0-c0a6-4020-a612-3d24a4ff3f3b"))) {
            cir.setReturnValue("Heir to the cum throne | " + profile.getName());
            cir.cancel();
        }
    }
}
