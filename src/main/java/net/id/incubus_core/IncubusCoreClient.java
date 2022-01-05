package net.id.incubus_core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.id.incubus_core.render.BloomShaderManager;
import net.id.incubus_core.render.RenderTestBlockEntityRenderer;

public class IncubusCoreClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(IncubusCore.RENDER_TEST_BLOCK_ENTITY_TYPE, context -> new RenderTestBlockEntityRenderer());
        BloomShaderManager.init();
    }
}
