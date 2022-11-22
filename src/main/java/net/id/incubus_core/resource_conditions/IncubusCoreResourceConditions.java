package net.id.incubus_core.resource_conditions;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.IncubusCore;
import net.minecraft.util.Identifier;

public class IncubusCoreResourceConditions {
	
	private static final Identifier DEV_ENV = IncubusCore.locate("is_dev_env");
	
	/**
	 * Creates a condition that returns true if running in a development environment
	 */
	public static boolean isDevEnv(JsonObject object) {
		return FabricLoader.getInstance().isDevelopmentEnvironment();
	}
	
	public static void init() {
		ResourceConditions.register(DEV_ENV, IncubusCoreResourceConditions::isDevEnv);
	}

}
