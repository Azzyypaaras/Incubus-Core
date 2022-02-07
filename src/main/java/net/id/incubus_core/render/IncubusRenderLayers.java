package net.id.incubus_core.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

import static net.id.incubus_core.IncubusCore.locate;

public class IncubusRenderLayers extends RenderLayer {
    public static final Target SOFT_BLOOM_TARGET = new Target(
            "soft_bloom_target",
            () -> {
                var frameBuffer = SoftBloomShaderManager.INSTANCE.getFramebuffer();
                frameBuffer.copyDepthFrom(MinecraftClient.getInstance().getFramebuffer());
                frameBuffer.beginWrite(false);
            },
            () -> MinecraftClient.getInstance().getFramebuffer().beginWrite(false)
    );

    public static final Target HARD_BLOOM_TARGET = new Target(
            "hard_bloom_target",
            () -> {
                var frameBuffer = HardBloomShaderManager.INSTANCE.getFramebuffer();
                frameBuffer.copyDepthFrom(MinecraftClient.getInstance().getFramebuffer());
                frameBuffer.beginWrite(false);
            },
            () -> MinecraftClient.getInstance().getFramebuffer().beginWrite(false)
    );

    public static final RenderLayer BLOOM_BASE = RenderLayerConstructor.buildMultiPhase("bloom_base", VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.QUADS, 256, false, true, MultiPhaseParameters.builder().texture(new Texture(locate("textures/special/blank.png"), false, false)).shader(RenderPhase.LIGHTNING_SHADER).transparency(RenderPhase.LIGHTNING_TRANSPARENCY).target(RenderPhase.WEATHER_TARGET).lightmap(DISABLE_LIGHTMAP).build(true));
    public static final RenderLayer SOFT_BLOOM_OVERLAY = RenderLayerConstructor.buildMultiPhase("soft_bloom_overlay", VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.QUADS, 256, false, true, MultiPhaseParameters.builder().texture(new Texture(locate("textures/special/blank.png"), false, false)).shader(RenderPhase.LIGHTNING_SHADER).transparency(RenderPhase.LIGHTNING_TRANSPARENCY).target(SOFT_BLOOM_TARGET).lightmap(DISABLE_LIGHTMAP).build(true));
    public static final RenderLayer HARD_BLOOM_OVERLAY = RenderLayerConstructor.buildMultiPhase("hard_bloom_overlay", VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.QUADS, 256, false, true, MultiPhaseParameters.builder().texture(new Texture(locate("textures/special/blank.png"), false, false)).shader(RenderPhase.LIGHTNING_SHADER).transparency(RenderPhase.LIGHTNING_TRANSPARENCY).target(HARD_BLOOM_TARGET).lightmap(DISABLE_LIGHTMAP).build(true));

    public IncubusRenderLayers(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }
}
