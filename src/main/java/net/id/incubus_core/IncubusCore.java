package net.id.incubus_core;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.id.incubus_core.misc.WorthinessChecker;
import net.id.incubus_core.misc.item.DebugFlameItem;
import net.id.incubus_core.misc.item.HandPistonItem;
import net.id.incubus_core.misc.IncubusToolMaterials;
import net.id.incubus_core.misc.item.LunarianSaberItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.render.RenderTestBlock;
import net.id.incubus_core.render.RenderTestBlockEntity;
import net.id.incubus_core.systems.RegistryRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
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

		registerItem("lunarian_saber", new LunarianSaberItem(IncubusToolMaterials.LUNARIAN, 1, 0F, new FabricItemSettings()));
		registerItem("hand_piston", new HandPistonItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1), false));
		registerItem("hand_piston_advanced", new HandPistonItem(new FabricItemSettings().group(ItemGroup.TOOLS).fireproof().rarity(Rarity.RARE).maxCount(1), true));
		registerItem("debug_flame", new DebugFlameItem(new FabricItemSettings().fireproof().rarity(Rarity.EPIC).maxCount(1).equipmentSlot(stack -> EquipmentSlot.HEAD)));
		registerItem("render_test", new BlockItem(RENDER_TEST_BLOCK, new FabricItemSettings()));
		registerBE("render_test", RENDER_TEST_BLOCK_ENTITY_TYPE);
	}

	public static final Block RENDER_TEST_BLOCK = registerBlock("render_test", new RenderTestBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).lightLevel(5)));

	public static final BlockEntityType<RenderTestBlockEntity> RENDER_TEST_BLOCK_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(RenderTestBlockEntity::new, RENDER_TEST_BLOCK).build();

	public static Item registerItem(String name, Item item) {
		return Registry.register(Registry.ITEM, locate(name), item);
	}

	public static Block registerBlock(String name, Block item) {
		return Registry.register(Registry.BLOCK, locate(name), item);
	}

	public static BlockEntityType registerBE(String name, BlockEntityType item) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, locate(name), item);
	}

	public static Identifier locate(String path) {
		return new Identifier(MODID, path);
	}
}