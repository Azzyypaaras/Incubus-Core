package net.id.incubus_core;

import com.mojang.logging.*;
import net.fabricmc.api.*;
import net.fabricmc.loader.api.*;
import net.id.incubus_core.condition.*;
import net.id.incubus_core.dev.*;
import net.id.incubus_core.misc.*;
import net.id.incubus_core.misc.item.*;
import net.id.incubus_core.recipe.*;
import net.id.incubus_core.recipe.matchbook.*;
import net.id.incubus_core.resource_conditions.*;
import net.id.incubus_core.systems.*;
import net.id.incubus_core.util.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import org.slf4j.*;

import java.util.*;

public class IncubusCore implements ModInitializer {

	public static final String MODID = "incubus_core";
	public static final Logger LOG = LogUtils.getLogger();

	public static final SplittableRandom RANDOM = new SplittableRandom(System.currentTimeMillis());

	@Override
	public void onInitialize() {
		var tempRandom = new Random(System.currentTimeMillis());
		if(!FabricLoader.getInstance().isDevelopmentEnvironment() && tempRandom.nextInt(100) == 0)
			LOG.info(IncubusCoreInit.HOLY_CONST);

		WorthinessChecker.init();
		DefaultMaterials.init();
		RegistryRegistry.init();
		IncubusSounds.init();
		IncubusCoreItems.init();
		IncubusMatches.init();
		IncubusCondition.init();
		IncubusRecipeTypes.init();
		IncubusPlayerData.init();
		IncubusCoreResourceConditions.init();

		if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
			if (Config.getBoolean(locate("devtools"), true)) {
				DevInit.commonInit();
			}
		}
	}
	
	public static Item registerItem(String name, Item item) {
		return Registry.register(Registries.ITEM, locate(name), item);
	}

	public static Block registerBlock(String name, Block item) {
		return Registry.register(Registries.BLOCK, locate(name), item);
	}

	public static BlockEntityType<?> registerBE(String name, BlockEntityType<?> item) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, locate(name), item);
	}

	public static StatusEffect registerEffect(String name, StatusEffect item) {
		return Registry.register(Registries.STATUS_EFFECT, locate(name), item);
	}

	public static SoundEvent registerSoundEvent(String name) {
		var id = locate(name);
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}

	public static Identifier locate(String path) {
		return new Identifier(MODID, path);
	}
}
