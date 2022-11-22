package net.id.incubus_core.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.id.incubus_core.IncubusCore;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/Framebuffer;copyDepthFrom(Lnet/minecraft/client/gl/Framebuffer;)V"))
    public void preventDepthCopy(Framebuffer instance, Framebuffer framebuffer) {
        if (zonkCheck()) {
            instance.copyDepthFrom(framebuffer);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderLayer(Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack;DDDLnet/minecraft/util/math/Matrix4f;)V"))
    public void preventSolidRender(WorldRenderer instance, RenderLayer renderLayer, MatrixStack matrices, double d, double e, double f, Matrix4f positionMatrix) {
        if( zonkCheck() || renderLayer != RenderLayer.getSolid() || System.currentTimeMillis() % 1000 == 0) {
            this.renderLayer(renderLayer, matrices, d, e, f, positionMatrix);
        }
    }

    @Shadow public abstract void renderSky(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable);

    @Shadow protected abstract void renderLayer(RenderLayer renderLayer, MatrixStack matrices, double d, double e, double f, Matrix4f positionMatrix);

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clear(IZ)V"))
    public void preventClear(int mask, boolean getError) {
        if (zonkCheck()) {
            RenderSystem.clear(mask, getError);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V"))
    public void preventSkyRender(WorldRenderer instance, MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable) {
        if (zonkCheck()) {
            renderSky(matrices, projectionMatrix, tickDelta, camera, bl, runnable);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/BackgroundRenderer;render(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/world/ClientWorld;IF)V"))
    public void preventBackgroundRender(Camera camera, float tickDelta, ClientWorld world, int i, float f) {
        if (zonkCheck()) {
            BackgroundRenderer.render(camera, tickDelta, world, i, f);
        }
    }

    @Unique
    private boolean zonkCheck() {
        return !(MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity player && player.hasStatusEffect(IncubusCore.ZONKED));
    }
}
