package net.id.incubus_core;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.dev.DevInit;
import net.id.incubus_core.misc.WorthinessChecker;
import net.id.incubus_core.systems.RegistryRegistry;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;


public class IncubusCore implements ModInitializer {

	public static final String MODID = "incubus_core";
	public static final Logger LOG = LogManager.getLogger(MODID);

	@Override
	public void onInitialize() {
		var tempRandom = new Random(System.currentTimeMillis());
		if(!FabricLoader.getInstance().isDevelopmentEnvironment() && tempRandom.nextInt(100) == 0)
			LOG.info(IncubusCoreInit.HOLY_CONST);

		WorthinessChecker.init();
		RegistryRegistry.init();
		
		if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
			DevInit.commonInit();
		}
	}

	public static Identifier locate(String path) {
		return new Identifier(MODID, path);
	}
}
