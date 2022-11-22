package net.id.incubus_core.render;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;

public class ColorUtils {

    public static int toHex(Vec3i color) {
        return MathHelper.packRgb(color.getX(), color.getY(), color.getZ());
    }

    public static int toHexWithAlpha(int r, int g, int b, int a) {
        return MathHelper.packRgb(r, g, b) | (a << 24);
    }

    public static Vec3i toRGB(int hex) {
        return new Vec3i((hex & 0xFF0000) >> 16, (hex & 0xFF00) >> 8, (hex & 0xFF));
    }
}
