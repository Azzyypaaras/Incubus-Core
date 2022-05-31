package net.id.incubus_core.render;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.util.RenderLayerHelper;
import ladysnake.satin.mixin.client.render.RenderLayerAccessor;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.id.incubus_core.IncubusCore;
import net.id.incubus_core.render.test.RenderTestBlockEntityRenderer;
import net.id.incubus_core.util.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

import static net.id.incubus_core.IncubusCore.locate;

// This extends RenderLayer only to get access to protected inner classes.
public class IncubusShaders extends RenderLayer {
    private static boolean renderingBloom = false;

    public static final ManagedShaderEffect SOFT_BLOOM = ShaderEffectManager.getInstance().manage(new Identifier("shaders/post/soft_bloom.json"));
    public static final ManagedShaderEffect HARD_BLOOM = ShaderEffectManager.getInstance().manage(new Identifier("shaders/post/hard_bloom.json"));
    private static final ManagedFramebuffer SOFT_BLOOM_BUFFER = SOFT_BLOOM.getTarget("light_sources");
    private static final ManagedFramebuffer HARD_BLOOM_BUFFER = HARD_BLOOM.getTarget("light_sources");
    
    // We need to do this outside of Satin's normal APIs because we require something they don't support.
    public static final RenderLayer SOFT_BLOOM_RENDER_LAYER = RenderLayerAccessor.satin$of(
        "incubus_core_soft_bloom_overlay",
        VertexFormats.POSITION_COLOR,
        VertexFormat.DrawMode.QUADS,
        256,
        false,
        true,
        MultiPhaseParameters.builder()
            .texture(new Texture(locate("textures/special/blank.png"), false, false))
            .shader(RenderPhase.LIGHTNING_SHADER)
            .transparency(RenderPhase.LIGHTNING_TRANSPARENCY)
            .target(new Target(
                "incubus_core_soft_bloom_target",
                () -> {
                    SOFT_BLOOM_BUFFER.copyDepthFrom(MinecraftClient.getInstance().getFramebuffer());
                    SOFT_BLOOM_BUFFER.beginWrite(false);
                },
                () -> MinecraftClient.getInstance().getFramebuffer().beginWrite(false)
            ))
            .lightmap(DISABLE_LIGHTMAP)
            .build(true)
    );
    public static final RenderLayer HARD_BLOOM_RENDER_LAYER = RenderLayerAccessor.satin$of(
        "incubus_core_hard_bloom_overlay",
        VertexFormats.POSITION_COLOR,
        VertexFormat.DrawMode.QUADS,
        256,
        false,
        true,
        MultiPhaseParameters.builder()
            .texture(new Texture(locate("textures/special/blank.png"), false, false))
            .shader(RenderPhase.LIGHTNING_SHADER)
            .transparency(RenderPhase.LIGHTNING_TRANSPARENCY)
            .target(new Target(
                "incubus_core_hard_bloom_target",
                () -> {
                    HARD_BLOOM_BUFFER.copyDepthFrom(MinecraftClient.getInstance().getFramebuffer());
                    HARD_BLOOM_BUFFER.beginWrite(false);
                },
                () -> MinecraftClient.getInstance().getFramebuffer().beginWrite(false)
            ))
            .lightmap(DISABLE_LIGHTMAP)
            .build(true)
    );

    public static final RenderLayer BLOOM_BASE = RenderLayerConstructor.buildMultiPhase("bloom_base", VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.QUADS, 256, false, true, RenderLayer.MultiPhaseParameters.builder().texture(new RenderPhase.Texture(locate("textures/special/blank.png"), false, false)).shader(RenderPhase.LIGHTNING_SHADER).transparency(RenderPhase.LIGHTNING_TRANSPARENCY).target(RenderPhase.WEATHER_TARGET).lightmap(DISABLE_LIGHTMAP).build(true));

    private IncubusShaders(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
        throw new UnsupportedOperationException("Stop. What are you doing?");
    }

    // Call me to enable bloom
    public static void enableBloom() {
        renderingBloom = true;
    }

    public static void init() {
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            if (renderingBloom) {
                SOFT_BLOOM.render(tickDelta);
                HARD_BLOOM.render(tickDelta);
                SOFT_BLOOM_BUFFER.clear(MinecraftClient.IS_SYSTEM_MAC);
                HARD_BLOOM_BUFFER.clear(MinecraftClient.IS_SYSTEM_MAC);
            }
        });

        if (Config.getBoolean(locate("enable_bloom"), false)) {
            enableBloom();
        }

        RenderLayerHelper.registerBlockRenderLayer(SOFT_BLOOM_RENDER_LAYER);
        RenderLayerHelper.registerBlockRenderLayer(HARD_BLOOM_RENDER_LAYER);

        // Don't remove this next line, or you may embarrass yourself in front of Pyrofab
        BlockEntityRendererRegistry.register(IncubusCore.RENDER_TEST_BLOCK_ENTITY_TYPE, context -> new RenderTestBlockEntityRenderer());
        BlockRenderLayerMap.INSTANCE.putBlock(IncubusCore.RENDER_TEST_BLOCK, RenderLayer.getTranslucent());
    }
}
