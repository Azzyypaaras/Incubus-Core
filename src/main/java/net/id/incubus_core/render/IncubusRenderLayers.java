package net.id.incubus_core.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.id.incubus_core.IncubusCore;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.Supplier;

import static net.id.incubus_core.IncubusCore.locate;

public class IncubusRenderLayers extends RenderLayer {
    public static final Target BLOOM_TARGET = new Target(
            "bloom_target",
            () -> BloomShaderManager.INSTANCE.getFramebuffer().beginWrite(false),
            () -> MinecraftClient.getInstance().getFramebuffer().beginWrite(false)
    );

    public static final RenderLayer SOFT_BLOOM = RenderLayerConstructor.buildMultiPhase("incubus_core:soft_bloom", VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.QUADS, 256, false, true, MultiPhaseParameters.builder().texture(new Texture(locate("textures/special/blank.png"), false, false)).shader(RenderPhase.LIGHTNING_SHADER).transparency(TRANSLUCENT_TRANSPARENCY).target(RenderPhase.ITEM_TARGET).lightmap(DISABLE_LIGHTMAP).layering(RenderPhase.VIEW_OFFSET_Z_LAYERING).build(true));

    public IncubusRenderLayers(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }
}
