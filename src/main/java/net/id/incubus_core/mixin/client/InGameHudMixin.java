package net.id.incubus_core.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.id.incubus_core.render.OverlayRegistrar;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public abstract class InGameHudMixin {

    @Shadow
    protected abstract void renderOverlay(MatrixStack matrices, Identifier texture, float opacity);

    @Inject(method = "renderHotbar", at = @At("HEAD"))
    public void renderOverlay(float tickDelta, MatrixStack matrices, CallbackInfo ci) {
        List<OverlayRegistrar.Overlay> overlays = OverlayRegistrar.getOverlays();
        Entity entity = MinecraftClient.getInstance().cameraEntity;
        if (entity instanceof LivingEntity player) {
            overlays.forEach(overlay -> {
                if (overlay.renderPredicate().test(player)) {
                    renderOverlay(matrices, overlay.path(), overlay.opacityProvider().apply(player));
                }
            });
        }
    }
}
