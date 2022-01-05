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

        float r = (float) Math.sin((entity.getWorld().getTime() + tickDelta) / 20) / 2 + 0.5F;
        float g = (float) Math.sin((entity.getWorld().getTime() + tickDelta) / 20 + (Math.PI / 2)) / 2 + 0.5F;
        float b = (float) Math.sin((entity.getWorld().getTime() + tickDelta) / 20+ Math.PI) / 2 + 0.5F;

        RenderHelper.drawEmissiveCube(vertexConsumers, matrices, new Vec3f(r, g, b), 0.5f);
        RenderHelper.drawBloomCube(vertexConsumers, matrices, new Vec3f(r, g, b), 0.5f);
        matrices.pop();
    }


}
