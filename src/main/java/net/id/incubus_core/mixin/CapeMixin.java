package net.id.incubus_core.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(AbstractClientPlayerEntity.class)
public class CapeMixin {
    @Inject(
            method = "getCapeTexture",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getCapeTexture(CallbackInfoReturnable<Identifier> cir){
        cir.setReturnValue(new Identifier("incubus_core", "textures/capes/lunarian.png"));
    }
}
