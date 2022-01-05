package net.id.incubus_core.mixin.client;

import net.id.incubus_core.render.BloomShaderManager;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;drawEntityOutlinesFramebuffer()V"))
    private void renderBloomEffect(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        ShaderEffect effect = BloomShaderManager.INSTANCE.getEffect();
        if (effect != null) {
            effect.render(tickDelta);
        }
    }

    @Inject(method = "onResized", at = @At("TAIL"))
    private void onResized(int width, int height, CallbackInfo ci) {
        ShaderEffect effect = BloomShaderManager.INSTANCE.getEffect();
        if (effect != null) {
            effect.setupDimensions(width, height);
        }
    }
}
