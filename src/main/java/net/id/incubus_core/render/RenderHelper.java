package net.id.incubus_core.render;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.Vec3i;


public class RenderHelper {

    public static void drawLaser(VertexConsumerProvider vertexConsumers, MatrixStack matrices, Vec3f color, float trans, float length, float scale, float offset, Direction direction) {
        matrices.translate(offset, offset, offset);
        directionalMatrixMultiply(matrices, direction);
        matrices.translate(0F, 0F, offset);
        matrices.translate(-scale / 2F, -scale / 2F, 0F);
        matrices.scale(scale, scale, length);
        RenderHelper.drawEmissiveCube(vertexConsumers, matrices, color, trans);
    }

    public static void drawEmissiveCube(VertexConsumerProvider vertexConsumers, MatrixStack matrices, Vec3f color, float trans) {
        MatrixStack.Entry matrix = matrices.peek();
        VertexConsumer consumer = vertexConsumers.getBuffer(IncubusShaders.BLOOM_BASE);


        //north
        consumer.vertex(matrix.getPositionMatrix(), 0, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 0, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();

        //east
        consumer.vertex(matrix.getPositionMatrix(), 1, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();

        //south
        consumer.vertex(matrix.getPositionMatrix(), 1, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 0, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 0, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();

        //west
        consumer.vertex(matrix.getPositionMatrix(), 0, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 0, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 0, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 0, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();

        //down
        consumer.vertex(matrix.getPositionMatrix(), 0, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 0, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();

        //up
        consumer.vertex(matrix.getPositionMatrix(), 0, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 0, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
    }

    public static void drawBloomCube(VertexConsumerProvider vertexConsumers, MatrixStack matrices, Vec3f color, float trans, boolean hard) {
        MatrixStack.Entry matrix = matrices.peek();
        VertexConsumer consumer = vertexConsumers.getBuffer(hard ? IncubusShaders.HARD_BLOOM_RENDER_LAYER : IncubusShaders.SOFT_BLOOM_RENDER_LAYER);

        //north
        consumer.vertex(matrix.getPositionMatrix(), 0, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 0, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();

        //east
        consumer.vertex(matrix.getPositionMatrix(), 1, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();

        //south
        consumer.vertex(matrix.getPositionMatrix(), 1, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 0, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 0, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();

        //west
        consumer.vertex(matrix.getPositionMatrix(), 0, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 0, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 0, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 0, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();

        //down
        consumer.vertex(matrix.getPositionMatrix(), 0, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 0, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();

        //up
        consumer.vertex(matrix.getPositionMatrix(), 0, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 1, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getPositionMatrix(), 0, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
    }

    public static void directionalMatrixOffset(MatrixStack matrices, Direction direction, float offset) {
        Vec3i vector = direction.getVector();
        matrices.translate(vector.getX() * offset, vector.getY() * offset, vector.getZ() * offset);
    }

    public static void directionalMatrixMultiply(MatrixStack matrices, Direction direction) {
        switch (direction) {
            case NORTH -> matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
            case SOUTH -> {}
            case EAST -> matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
            case WEST -> matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270));
            case UP -> matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(270));
            case DOWN -> matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
        }
    }

    public static float rfh(int hex) {
        return (hex & 0xFF0000) >> 16;

    }

    public static float gfh(int hex) {
        return (hex & 0xFF00) >> 8;
    }

    public static float bfh(int hex) {
        return (hex & 0xFF);
    }
}
