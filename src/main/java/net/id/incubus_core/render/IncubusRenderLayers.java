package net.id.incubus_core.render;

import net.id.incubus_core.mixin.client.RenderLayerAccessor;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

import static net.id.incubus_core.IncubusCore.locate;

public class IncubusRenderLayers extends RenderLayer {

    public static final RenderLayer EMISSIVE = RenderLayerAccessor.incubus$of(
            "bloom_base",
            VertexFormats.POSITION_COLOR,
            VertexFormat.DrawMode.QUADS,
            256,
            false,
            true,
            RenderLayer.MultiPhaseParameters.builder()
                    .texture(new RenderPhase.Texture(locate("textures/special/blank.png"), false, false))
                    .shader(RenderPhase.LIGHTNING_SHADER)
                    .transparency(RenderPhase.LIGHTNING_TRANSPARENCY)
                    .target(RenderPhase.WEATHER_TARGET)
                    .lightmap(DISABLE_LIGHTMAP)
                    .build(true)
    );

    /**
     * Do not.
     */
    public IncubusRenderLayers(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
        throw new IllegalAccessError("why would you try this");
    }
}
