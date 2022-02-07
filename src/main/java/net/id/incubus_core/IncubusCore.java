package net.id.incubus_core;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.dev.DevInit;
import net.id.incubus_core.misc.WorthinessChecker;
import net.id.incubus_core.potion.ZonkedEffect;
import net.id.incubus_core.systems.RegistryRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
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

	public static final StatusEffect ZONKED = registerEffect("zonked", new ZonkedEffect());

	public static Item registerItem(String name, Item item) {
		return Registry.register(Registry.ITEM, locate(name), item);
	}

	public static Block registerBlock(String name, Block item) {
		return Registry.register(Registry.BLOCK, locate(name), item);
	}

	public static BlockEntityType<?> registerBE(String name, BlockEntityType<?> item) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, locate(name), item);
	}

	public static StatusEffect registerEffect(String name, StatusEffect item) {
		return Registry.register(Registry.STATUS_EFFECT, locate(name), item);
	}

	public static Identifier locate(String path) {
		return new Identifier(MODID, path);
	}
}
