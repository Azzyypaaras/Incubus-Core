package net.id.incubus_core.mixin.client;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.SpriteContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpriteContents.class)
public interface SpriteContentsAccessor {

    @Invoker("upload")
    void callUpload(int x, int y, int unpackSkipPixels, int unpackSkipRows, NativeImage[] images);
}
