package net.id.incubus_core.mixin.client;

import net.minecraft.client.texture.Sprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Sprite.AnimationFrame.class)
public interface Sprite$AnimationFrameAccessor {
    @Accessor
    int getTime();

    @Accessor
    int getIndex();
}
