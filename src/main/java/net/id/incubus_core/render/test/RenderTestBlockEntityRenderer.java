package net.id.incubus_core.render.test;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.id.incubus_core.render.RenderHelper;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class RenderTestBlockEntityRenderer implements BlockEntityRenderer<RenderTestBlockEntity> {
    public RenderTestBlockEntityRenderer(BlockEntityRendererFactory.Context context){
    }
    
    @Override
    public void render(RenderTestBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        
        var world = entity.getWorld();
        var time = (world.getTime() + tickDelta) / 20;
        
        float r = (float) Math.sin(time) / 2 + 0.5F;
        float g = (float) Math.sin(time + (Math.PI / 2)) / 2 + 0.5F;
        float b = (float) Math.sin(time + Math.PI) / 2 + 0.5F;
        
        RenderHelper.drawEmissiveCube(vertexConsumers, matrices, new Vec3f(r, g, b), 0.5F);
        RenderHelper.drawBloomCube(vertexConsumers, matrices, new Vec3f(r, g, b), 0.35F);
        matrices.pop();
    }
}
