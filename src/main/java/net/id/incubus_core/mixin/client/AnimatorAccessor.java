package net.id.incubus_core.mixin.client;

import net.minecraft.client.texture.SpriteContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SpriteContents.AnimatorImpl.class)
public interface AnimatorAccessor {
    @Accessor
    int getFrame();

    @Accessor
    int getCurrentTime();

    @Accessor
    SpriteContents.Animation getAnimation();
}