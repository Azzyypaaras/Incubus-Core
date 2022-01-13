package net.id.incubus_core.mixin.client;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.Sprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Sprite.class)
public interface SpriteAccessor {

    @Invoker("upload")
    void callUpload(int frameX, int frameY, NativeImage[] images);
}
