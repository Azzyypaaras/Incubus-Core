package net.id.incubus_core.mixin.client;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.Sprite;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.texture.Sprite$Interpolation")
public abstract class InterpFixMixin {

    @Shadow protected abstract int getPixelColor(Sprite.Animation animation, int frameIndex, int layer, int x, int y);

    @Shadow protected abstract int lerp(double delta, int to, int from);

    @Shadow @Final private NativeImage[] images;

    @Unique
    Sprite parent$this;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void assignParent(Sprite parent, Sprite.Info info, int maxLevel, CallbackInfo ci) {
        this.parent$this = parent;
    }

    /**
     * @author Azzy
     * @reason My nuts itch, also translucent interp is borked.
     */
    @Overwrite
    public void apply(Sprite.Animation inputAnimation) {
        var animation = (Sprite.Animation & Sprite$AnimationAccessor) inputAnimation;
        var animationFrame = (Sprite.AnimationFrame & Sprite$AnimationFrameAccessor) animation.getFrames().get(animation.getFrameIndex());
        double d = 1.0D - (double)animation.getFrameTicks() / (double)animationFrame.getTime();
        int i = animationFrame.getIndex();
        int j = ((Sprite.AnimationFrame & Sprite$AnimationFrameAccessor) animation.getFrames().get((animation.getFrameIndex() + 1) % animation.getFrames().size())).getIndex();
        if (i != j) {
            for(int k = 0; k < this.images.length; ++k) {
                int l = parent$this.getWidth() >> k;
                int m = parent$this.getHeight() >> k;

                for(int n = 0; n < m; ++n) {
                    for(int o = 0; o < l; ++o) {
                        int p = this.getPixelColor(animation, i, k, o, n);
                        int q = this.getPixelColor(animation, j, k, o, n);
                        int a = this.lerp(d, p >> 24 & 255, q >> 24 & 255);
                        int r = this.lerp(d, p >> 16 & 255, q >> 16 & 255);
                        int g = this.lerp(d, p >> 8 & 255, q >> 8 & 255);
                        int b = this.lerp(d, p & 255, q & 255);
                        this.images[k].setColor(o, n, (a << 24) | (r << 16) | (g << 8) | b);
                    }
                }
            }

            parent$this.upload(0, 0, this.images);
        }

    }
}
