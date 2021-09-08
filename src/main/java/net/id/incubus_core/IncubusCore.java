package net.id.incubus_core;

import net.id.incubus_core.misc.HandPistonItem;
import net.id.incubus_core.misc.IncubusToolMaterials;
import net.id.incubus_core.misc.IncubusItemGroups;
import net.id.incubus_core.misc.LunarianSaberItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.systems.DefaultMaterials;
import net.id.incubus_core.systems.HeatHelper;
import net.id.incubus_core.systems.RegistryRegistry;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class IncubusCore implements ModInitializer {

	public static final String MODID = "incubus_core";
	public static final Logger LOG = LogManager.getLogger(MODID);
	public static final Item LUNARIAN_SABER = registerItem("lunarian_saber", new LunarianSaberItem(IncubusToolMaterials.LUNARIAN, 1, 0F, new FabricItemSettings().group(IncubusItemGroups.CORE_GROUP)));

	public static boolean bypassWorthiness;

	@Override
	public void onInitialize() {
		if(!FabricLoader.getInstance().isDevelopmentEnvironment())
			LOG.info(IncubusCoreInit.HOLY_CONST);

		genCheck: {
			String[] args = FabricLoader.getInstance().getLaunchArguments(false);
			for (String arg : args) {
				if(arg.equals("WORTHY")) {
					bypassWorthiness = true;
					break genCheck;
				}
			}
			bypassWorthiness = false;
		}

		RegistryRegistry.init();

		registerItem("hand_piston", new HandPistonItem(new FabricItemSettings().group(IncubusItemGroups.CORE_GROUP).maxCount(1), false));
		registerItem("hand_piston_advanced", new HandPistonItem(new FabricItemSettings().group(IncubusItemGroups.CORE_GROUP).fireproof().rarity(Rarity.RARE).maxCount(1), true));
	}

	public static Item registerItem(String name, Item item) {
		return Registry.register(Registry.ITEM, new Identifier(MODID, name), item);
	}

	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
}