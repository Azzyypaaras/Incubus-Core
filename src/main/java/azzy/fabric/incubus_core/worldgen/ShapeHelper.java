package azzy.fabric.incubus_core.worldgen;

import net.minecraft.util.math.BlockPos;

public class ShapeHelper {

    public static boolean testSphere(int r, BlockPos test, BlockPos center) {
        return Math.abs(center.getSquaredDistance(test)) <= Math.pow(r, 2);
    }

    public static boolean testElipsoid(int a, int b, int c, BlockPos test, BlockPos center) {
        int x = Math.abs(test.getX() - center.getX());
        int y = Math.abs(test.getY() - center.getY());
        int z = Math.abs(test.getZ() - center.getZ());
        return Math.pow(x, 2) / Math.pow(a, 2) + Math.pow(y, 2) / Math.pow(b, 2) + Math.pow(z, 2) / Math.pow(c, 2)  <= 1;
    }
}
