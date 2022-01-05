package net.id.incubus_core.render;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

public class RenderTestBlockEntityRenderer implements BlockEntityRenderer<RenderTestBlockEntity> {

    @Override
    public void render(RenderTestBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        MatrixStack.Entry matrix = matrices.peek();
        VertexConsumer consumer = vertexConsumers.getBuffer(IncubusRenderLayers.SIMPLE_BLOOM);

        matrices.translate(0, 1, 0);

        var color = new Vec3f(1, 1, 1);
        var trans = 1f;

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

        matrices.pop();
    }
}
