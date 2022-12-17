package net.id.incubus_core.mixin.client;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.SpriteContents;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpriteContents.Interpolation.class)
public abstract class InterpFixMixin {

    @Shadow protected abstract int getPixelColor(SpriteContents.Animation animation, int frameIndex, int layer, int x, int y);

    @Shadow protected abstract int lerp(double delta, int to, int from);

    @Shadow @Final private NativeImage[] images;

    @Unique
    SpriteContents parent$this;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void assignParent(SpriteContents spriteContents, CallbackInfo ci) {
        this.parent$this = spriteContents;
    }

    // replace this with an inject or redirect, so we don't need to accesswiden Sprite.Animation and Sprite.AnimationFrame
    /**
     * @author Azzy
     * @reason My nuts itch, also translucent interp is borked.
     */
    @Overwrite
    public void apply(int x, int y, SpriteContents.AnimatorImpl animator) {
        var accessor = (AnimatorAccessor) animator;
        var animation = ((AnimationAccessor) accessor.getAnimation());

        var animationFrame =  animation.getFrames().get(accessor.getFrame());
        double d = 1.0D - accessor.getCurrentTime() / (double)((AnimationFrameAccessor) animationFrame).getTime();
        int i = ((AnimationFrameAccessor) animationFrame).getIndex();
        int j = ((AnimationFrameAccessor) animation.getFrames().get((accessor.getFrame() + 1) % animation.getFrames().size())).getIndex();
        if (i != j) {
            for(int k = 0; k < this.images.length; ++k) {
                int l = parent$this.getWidth() >> k;
                int m = parent$this.getHeight() >> k;

                for(int n = 0; n < m; ++n) {
                    for(int o = 0; o < l; ++o) {
                        int p = this.getPixelColor(accessor.getAnimation(), i, k, o, n);
                        int q = this.getPixelColor(accessor.getAnimation(), j, k, o, n);
                        int a = this.lerp(d, p >> 24 & 255, q >> 24 & 255);
                        int r = this.lerp(d, p >> 16 & 255, q >> 16 & 255);
                        int g = this.lerp(d, p >> 8 & 255, q >> 8 & 255);
                        int b = this.lerp(d, p & 255, q & 255);
                        this.images[k].setColor(o, n, (a << 24) | (r << 16) | (g << 8) | b);
                    }
                }
            }

            ((SpriteContentsAccessor) parent$this).callUpload(x, y, 0, 0, this.images);
        }

    }
}
