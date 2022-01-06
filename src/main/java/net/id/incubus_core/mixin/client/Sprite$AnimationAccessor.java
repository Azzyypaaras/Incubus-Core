package net.id.incubus_core.mixin.client;

import net.minecraft.client.texture.Sprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(targets = "net.minecraft.client.texture.Sprite$Animation")
public interface Sprite$AnimationAccessor {
    @Accessor
    int getFrameIndex();

    @Accessor
    int getFrameTicks();

    @Accessor
    List<Sprite.AnimationFrame> getFrames();
}
