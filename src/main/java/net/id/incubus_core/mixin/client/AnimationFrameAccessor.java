package net.id.incubus_core.mixin.client;

import net.minecraft.client.texture.SpriteContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SpriteContents.AnimationFrame.class)
public interface AnimationFrameAccessor {
    @Accessor
    int getTime();

    @Accessor
    int getIndex();
}
