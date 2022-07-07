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
import net.id.incubus_core.render.test.RenderTestBlock;
import net.id.incubus_core.render.test.RenderTestBlockEntity;
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

	public static final Block RENDER_TEST_BLOCK = registerBlock("render_test", new RenderTestBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).nonOpaque().luminance(5).blockVision((state, world, pos) -> false).allowsSpawning((state, world, pos, type) -> false).suffocates((state, world, pos) -> false).solidBlock((state, world, pos) -> false)));
	public static final BlockEntityType<RenderTestBlockEntity> RENDER_TEST_BLOCK_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(RenderTestBlockEntity::new, RENDER_TEST_BLOCK).build();

	public static final SoundEvent DUPED_SHOVELS = registerSoundEvent("duped_shovels");

	@Override
	public void onInitialize() {
		var tempRandom = new Random(System.currentTimeMillis());
		if(!FabricLoader.getInstance().isDevelopmentEnvironment() && tempRandom.nextInt(100) == 0) {
			LOG.info(IncubusCoreInit.HOLY_CONST);
		}

		WorthinessChecker.init();
		RegistryRegistry.init();
		IncubusCondition.init();
		IncubusRecipes.init();

		registerItem("lunarian_saber", new LunarianSaberItem(IncubusToolMaterials.LUNARIAN, 1, 0F, new FabricItemSettings()));
		registerItem("hand_piston", new HandPistonItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1), false));
		registerItem("hand_piston_advanced", new HandPistonItem(new FabricItemSettings().group(ItemGroup.TOOLS).fireproof().rarity(Rarity.RARE).maxCount(1), true));
		registerItem("debug_flame", new DebugFlameItem(new FabricItemSettings().fireproof().rarity(Rarity.EPIC).maxCount(1).equipmentSlot(stack -> EquipmentSlot.HEAD)));
		registerItem("sacred_disc_1", new IncubusMusicDiscItem(0, DUPED_SHOVELS, new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.EPIC)));
		registerItem("render_test", new BlockItem(RENDER_TEST_BLOCK, new FabricItemSettings()));

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
