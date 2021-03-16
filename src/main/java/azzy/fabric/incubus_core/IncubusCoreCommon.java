package azzy.fabric.incubus_core;

import azzy.fabric.incubus_core.datagen.Metadata;
import azzy.fabric.incubus_core.datagen.RecipeJsonGen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class IncubusCoreCommon implements ModInitializer {

	public static final String MODID = "incubus_core";
	public static final Logger LOG = LogManager.getLogger(MODID);

	@Override
	public void onInitialize() {
		if(!FabricLoader.getInstance().isDevelopmentEnvironment())
			LOG.info(IncubusCoreInit.HOLY_CONST);
	}
}