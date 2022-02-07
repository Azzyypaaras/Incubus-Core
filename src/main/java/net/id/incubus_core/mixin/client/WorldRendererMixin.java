package net.id.incubus_core.mixin.client;

import net.id.incubus_core.render.HardBloomShaderManager;
import net.id.incubus_core.render.SoftBloomShaderManager;
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
        ShaderEffect hard = HardBloomShaderManager.INSTANCE.getEffect();
        if (hard != null) {
            hard.setupDimensions(width, height);
        }

        ShaderEffect soft = SoftBloomShaderManager.INSTANCE.getEffect();
        if (soft != null) {
            soft.setupDimensions(width, height);
        }
    }

    @Inject(method = "close", at = @At("TAIL"))
    private void close(CallbackInfo ci) {
        ShaderEffect hard = HardBloomShaderManager.INSTANCE.getEffect();
        if (hard != null) {
            hard.close();
        }

        ShaderEffect soft = SoftBloomShaderManager.INSTANCE.getEffect();
        if (soft != null) {
            soft.close();
        }
    }
}
