package azzy.fabric.incubus_core.render;

import azzy.fabric.incubus_core.IncubusCoreCommon;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;

public class IncubusRenderLayers extends RenderLayer {

    public IncubusRenderLayers(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    public static final RenderLayer SOFT_BLOOM = RenderLayerConstructor.buildMultiPhase("incubus_core:soft_bloom", VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.QUADS, 256, false, true, MultiPhaseParameters.builder().texture(new Texture(new Identifier(IncubusCoreCommon.MODID, "textures/special/blank.png"), false, false)).shader(RenderPhase.LIGHTNING_SHADER).transparency(TRANSLUCENT_TRANSPARENCY).target(RenderPhase.ITEM_TARGET).lightmap(DISABLE_LIGHTMAP).layering(RenderPhase.VIEW_OFFSET_Z_LAYERING).build(true));
}
