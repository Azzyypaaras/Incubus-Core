package net.id.incubus_core.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import ladysnake.satin.api.event.PostWorldRenderCallbackV2;
import ladysnake.satin.api.managed.ManagedFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import ladysnake.satin.api.util.RenderLayerHelper;
import ladysnake.satin.mixin.client.render.RenderLayerAccessor;
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
@SuppressWarnings("UnstableApiUsage")
public class IncubusShaders extends RenderLayer {
    private static boolean renderingBloom = false;

    public static final ManagedShaderEffect BLOOM = ShaderEffectManager.getInstance().manage(new Identifier("shaders/post/bloom.json"));
    private static final ManagedFramebuffer BLOOM_BUFFER = BLOOM.getTarget("light_sources");

    private static final Uniform1f BLOOM_RADIUS = BLOOM.findUniform1f("Radius");
    private static final Uniform1f BLOOM_DIVISOR = BLOOM.findUniform1f("Divisor");

    // We need to do this outside of Satin's normal APIs because we require something they don't support.
    public static final RenderLayer BLOOM_RENDER_LAYER = RenderLayerAccessor.satin$of(
        "incubus_core_bloom_overlay",
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
                "incubus_core_bloom_target",
                () -> {
                    BLOOM_BUFFER.copyDepthFrom(MinecraftClient.getInstance().getFramebuffer());
                    BLOOM_BUFFER.beginWrite(false);
                },
                () -> MinecraftClient.getInstance().getFramebuffer().beginWrite(false)
            ))
            .lightmap(DISABLE_LIGHTMAP)
            .build(true)
    );

    public static final RenderLayer BLOOM_BASE = RenderLayerAccessor.satin$of(
        "bloom_base",
        VertexFormats.POSITION_COLOR,
        VertexFormat.DrawMode.QUADS,
        256, 
        false,
        true,
        MultiPhaseParameters.builder()
            .texture(new RenderPhase.Texture(locate("textures/special/blank.png"), false, false))
            .shader(RenderPhase.LIGHTNING_SHADER)
            .transparency(RenderPhase.LIGHTNING_TRANSPARENCY)
            .target(RenderPhase.WEATHER_TARGET)
            .lightmap(DISABLE_LIGHTMAP)
            .build(true)
    );

    private IncubusShaders(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
        throw new UnsupportedOperationException("Stop. What are you doing?");
    }

    // Call me to enable bloom
    public static void enableBloom() {
        if(!renderingBloom) {
            initBloom();
        }
        renderingBloom = true;
    }
    
    private static void initBloom() {
        PostWorldRenderCallbackV2.EVENT.register((matrixStack, camera, tickDelta, nanoTime)->{
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE);
            BLOOM_RADIUS.set(10);
            BLOOM_DIVISOR.set(24);
            BLOOM.render(tickDelta);
            BLOOM_BUFFER.clear(MinecraftClient.IS_SYSTEM_MAC);
            MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
            RenderSystem.disableBlend();
        });
    }
    
    public static void init() {
        if (Config.getBoolean(locate("enable_bloom"), false)) {
            enableBloom();
        }

        RenderLayerHelper.registerBlockRenderLayer(BLOOM_RENDER_LAYER);

        // Don't remove this next line, or you may embarrass yourself in front of Pyrofab
        // Bloom doesn't work with baked quads right now, that needs to be fixed at some point.
        BlockEntityRendererRegistry.register(IncubusCore.RENDER_TEST_BLOCK_ENTITY_TYPE, RenderTestBlockEntityRenderer::new);
    }
}
