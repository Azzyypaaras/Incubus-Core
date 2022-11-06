package net.id.incubus_core;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.condition.IncubusCondition;
import net.id.incubus_core.dev.DevInit;
import net.id.incubus_core.devel.IncubusDevel;
import net.id.incubus_core.misc.IncubusToolMaterials;
import net.id.incubus_core.misc.WorthinessChecker;
import net.id.incubus_core.misc.item.DebugFlameItem;
import net.id.incubus_core.misc.item.HandPistonItem;
import net.id.incubus_core.misc.item.IncubusMusicDiscItem;
import net.id.incubus_core.misc.item.LunarianSaberItem;
import net.id.incubus_core.potion.ZonkedEffect;
import net.id.incubus_core.recipe.IncubusRecipes;
import net.id.incubus_core.recipe.matchbook.IncubusMatches;
import net.id.incubus_core.resource_conditions.IncubusCoreResourceConditions;
import net.id.incubus_core.systems.RegistryRegistry;
import net.id.incubus_core.util.Config;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;

import java.util.Random;
import java.util.SplittableRandom;


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
		RegistryRegistry.init();
		IncubusCoreItems.init();
		IncubusMatches.init();
		IncubusCondition.init();
		IncubusRecipes.init();
		IncubusPlayerData.init();
		IncubusCoreResourceConditions.init();

		if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
			IncubusDevel.init();
			if (Config.getBoolean(locate("devtools"), true)) {
				DevInit.commonInit();
			}
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

	public static SoundEvent registerSoundEvent(String name) {
		var id = locate(name);
		return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
	}

	public static Identifier locate(String path) {
		return new Identifier(MODID, path);
	}
}
