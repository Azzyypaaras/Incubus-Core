package net.id.incubus_core.mixin.client;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.Sprite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Sprite.Interpolation.class)
public abstract class InterpFixMixin {

    @Shadow protected abstract int getPixelColor(Sprite.Animation animation, int frameIndex, int layer, int x, int y);

    @Shadow protected abstract int lerp(double delta, int to, int from);

    @Shadow @Final private NativeImage[] images;

    @Final
    @Shadow
    Sprite field_21757;

    /**
     * @author Azzy
     * @reason My nuts itch, also translucent interp is borked.
     */
    @Overwrite
    public void apply(Sprite.Animation animation) {
        Sprite.AnimationFrame animationFrame = (Sprite.AnimationFrame)animation.frames.get(animation.frameIndex);
        double d = 1.0D - (double)animation.frameTicks / (double)animationFrame.time;
        int i = animationFrame.index;
        int j = ((Sprite.AnimationFrame)animation.frames.get((animation.frameIndex + 1) % animation.frames.size())).index;
        if (i != j) {
            for(int k = 0; k < this.images.length; ++k) {
                int l = field_21757.getWidth() >> k;
                int m = field_21757.getHeight() >> k;

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

            field_21757.upload(0, 0, this.images);
        }

    }
}
