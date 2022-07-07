package net.id.incubus_core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.dev.DevInit;
import net.id.incubus_core.devel.IncubusDevel;
import net.id.incubus_core.render.FancyBlockModelRegistry;
import net.id.incubus_core.render.IncubusShaders;
import net.id.incubus_core.util.Config;

import static net.id.incubus_core.IncubusCore.locate;

public class IncubusCoreClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        IncubusShaders.init();
        FancyBlockModelRegistry.init();

        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            IncubusDevel.initClient();
            if (Config.getBoolean(locate("devtools"), true))
                DevInit.clientInit();
        }
    }
}
