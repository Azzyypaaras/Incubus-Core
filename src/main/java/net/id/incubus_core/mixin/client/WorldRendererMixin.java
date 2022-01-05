package net.id.incubus_core.mixin.client;

import net.id.incubus_core.render.BloomShaderManager;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "onResized", at = @At("TAIL"))
    private void onResized(int width, int height, CallbackInfo ci) {
        ShaderEffect effect = BloomShaderManager.INSTANCE.getEffect();
        if (effect != null) {
            effect.setupDimensions(width, height);
        }
    }

    @Inject(method = "close", at = @At("TAIL"))
    private void close(CallbackInfo ci) {
        ShaderEffect effect = BloomShaderManager.INSTANCE.getEffect();
        if (effect != null) {
            effect.close();
        }
    }
}
