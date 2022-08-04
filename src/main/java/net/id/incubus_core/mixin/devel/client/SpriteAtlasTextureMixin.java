package net.id.incubus_core.mixin.devel.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.id.incubus_core.devel.IncubusDevel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Queue;

// the unmapped mixin targets break in prod
@Environment(EnvType.CLIENT)
@Mixin(SpriteAtlasTexture.class)
public abstract class SpriteAtlasTextureMixin {
    @Inject(
            method = "method_18160",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    ordinal = 0
            )
    )
    private void loadSprites$badMeta(Identifier identifier, ResourceManager resourceManager, Queue<Sprite.Info> queue, CallbackInfo ci){
        IncubusDevel.logBadTexture(identifier);
    }

    @Inject(
            method = "method_18160",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    ordinal = 1
            )
    )
    private void loadSprites$notFound(Identifier identifier, ResourceManager resourceManager, Queue<Sprite.Info> queue, CallbackInfo ci){
        IncubusDevel.logMissingTexture(identifier);
    }

    @Inject(
            method = "loadSprite",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    ordinal = 0
            )
    )
    private void loadSprite$badMeta(ResourceManager container, Sprite.Info info, int atlasWidth, int atlasHeight, int maxLevel, int x, int y, CallbackInfoReturnable<Sprite> cir){
        var identifier = info.getId();
        IncubusDevel.logBadTexture(identifier);
    }

    @Inject(
            method = "loadSprite",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    ordinal = 1
            )
    )
    private void loadSprite$notFound(ResourceManager container, Sprite.Info info, int atlasWidth, int atlasHeight, int maxLevel, int x, int y, CallbackInfoReturnable<Sprite> cir){
        var identifier = info.getId();
        IncubusDevel.logMissingTexture(identifier);
    }
}
