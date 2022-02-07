package net.id.incubus_core.dev;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.IncubusCore;
import net.id.incubus_core.dev.item.EntityDeathMessageTestItem;
import net.id.incubus_core.misc.IncubusToolMaterials;
import net.id.incubus_core.misc.item.DebugFlameItem;
import net.id.incubus_core.misc.item.HandPistonItem;
import net.id.incubus_core.misc.item.LunarianSaberItem;
import net.id.incubus_core.render.RenderTestBlock;
import net.id.incubus_core.render.RenderTestBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;

import static net.id.incubus_core.IncubusCore.*;
import static net.minecraft.item.Items.DIAMOND;

/**
 * Development only stuff.
 * <p>
 * Used for testing internal things without littering production environments.
 */
public final class DevInit {
    static {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment()) {
            throw new RuntimeException("Incubus Core development classes loaded in production, this should not happen!");
        }
    }
    
    private static final ItemGroup GROUP = FabricItemGroupBuilder.build(IncubusCore.locate("dev"), () -> new ItemStack(DIAMOND));
    private static final Item ENTITY_DEATH_MESSAGE_ITEM = new EntityDeathMessageTestItem(new FabricItemSettings().group(GROUP));
    public static final Block RENDER_TEST_BLOCK = registerBlock("render_test", new RenderTestBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).nonOpaque().luminance(5).blockVision((state, world, pos) -> false).allowsSpawning((state, world, pos, type) -> false).suffocates((state, world, pos) -> false).solidBlock((state, world, pos) -> false)));

    public static final BlockEntityType<RenderTestBlockEntity> RENDER_TEST_BLOCK_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(RenderTestBlockEntity::new, RENDER_TEST_BLOCK).build();

    public static void commonInit() {
        registerItem("lunarian_saber", new LunarianSaberItem(IncubusToolMaterials.LUNARIAN, 1, 0F, new FabricItemSettings()));
        registerItem("hand_piston", new HandPistonItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1), false));
        registerItem("hand_piston_advanced", new HandPistonItem(new FabricItemSettings().group(ItemGroup.TOOLS).fireproof().rarity(Rarity.RARE).maxCount(1), true));
        registerItem("debug_flame", new DebugFlameItem(new FabricItemSettings().fireproof().rarity(Rarity.EPIC).maxCount(1).equipmentSlot(stack -> EquipmentSlot.HEAD)));
        registerItem("render_test", new BlockItem(RENDER_TEST_BLOCK, new FabricItemSettings()));
        registerBE("render_test", RENDER_TEST_BLOCK_ENTITY_TYPE);
        registerItem("entity_death_message_item", ENTITY_DEATH_MESSAGE_ITEM);
    }
}
