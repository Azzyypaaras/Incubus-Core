package net.id.incubus_core.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.client.texture.Sprite$AnimationFrame")
public interface Sprite$AnimationFrameAccessor {
    @Accessor
    int getTime();

    @Accessor
    int getIndex();
}
