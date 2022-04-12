package net.id.incubus_core.mixin.woodtypefactory.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.id.incubus_core.woodtypefactory.api.chest.ChestFactory;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

/**
 * Adds custom chest textures to the TexturedRenderLayers.
 */
@Environment(EnvType.CLIENT)
@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {
    @Inject(
        method = "addDefaultTextures",
        at = @At("TAIL")
    )
    private static void addDefaultTextures(Consumer<SpriteIdentifier> adder, CallbackInfo ci){
        ChestFactory.addDefaultTextures(adder);
    }
}
