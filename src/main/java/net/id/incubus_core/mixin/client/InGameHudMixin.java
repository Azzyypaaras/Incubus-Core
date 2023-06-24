package net.id.incubus_core.mixin.client;

import net.fabricmc.api.*;
import net.id.incubus_core.render.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.hud.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public abstract class InGameHudMixin {

    @Shadow
    protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Inject(method = "renderHotbar", at = @At("HEAD"))
    public void renderOverlay(float tickDelta, DrawContext context, CallbackInfo ci) {
        List<OverlayRegistrar.Overlay> overlays = OverlayRegistrar.getOverlays();
        Entity entity = MinecraftClient.getInstance().cameraEntity;
        if (entity instanceof LivingEntity player) {
            overlays.forEach(overlay -> {
                if (overlay.renderPredicate().test(player)) {
                    renderOverlay(context, overlay.path(), overlay.opacityProvider().apply(player));
                }
            });
        }
    }
}
