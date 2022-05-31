package net.id.incubus_core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.dev.DevInit;
import net.id.incubus_core.devel.IncubusDevel;
import net.id.incubus_core.render.IncubusShaders;

public class IncubusCoreClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        IncubusShaders.init();

        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            IncubusDevel.initClient();
            DevInit.clientInit();
        }
    }
}
