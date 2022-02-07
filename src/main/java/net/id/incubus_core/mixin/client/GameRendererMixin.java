package net.id.incubus_core.mixin.client;

import net.id.incubus_core.dev.DevInit;
import net.id.incubus_core.render.HardBloomShaderManager;
import net.id.incubus_core.render.SoftBloomShaderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;drawEntityOutlinesFramebuffer()V"))
    private void renderBloomEffect(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        SoftBloomShaderManager.INSTANCE.render(tickDelta);
        HardBloomShaderManager.INSTANCE.render(tickDelta);
    }

    @Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"))
    private void renderZonk(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci) {
        if(client.getCameraEntity() instanceof LivingEntity entity && entity.hasStatusEffect(DevInit.ZONKED))
            HardBloomShaderManager.INSTANCE.render(tickDelta);
    }
}
