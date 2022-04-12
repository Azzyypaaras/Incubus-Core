package net.id.incubus_core.dev;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.IncubusCore;
import net.id.incubus_core.dev.item.EntityDeathMessageTestItem;
import net.id.incubus_core.woodtypefactory.api.WoodSettingsFactory;
import net.id.incubus_core.woodtypefactory.api.WoodTypeFactory;
import net.minecraft.block.MapColor;
import net.minecraft.block.sapling.OakSaplingGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

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

    private static final WoodTypeFactory INCUBUS_WOOD =
            WoodTypeFactory.of(
                    new WoodSettingsFactory(MapColor.BLACK, MapColor.BRIGHT_RED),
                    MODID, "incubus",
                    new OakSaplingGenerator(), // Not writing a whole sapling generator for this demo.
                    true, // Create a chest
                    true // Create leaves
            );
    /*
    You would probably still want to have static constants for your wood, like
    private static final PillarBlock INCUBUS_LOG = INCUBUS_WOOD.log();
    private static final PillarBlock INCUBUS_WOOD = INCUBUS_WOOD.wood();
    ... etc.

    I've opted not to do that in order to avoid clutter, since this is only a test.
     */

    public static void commonInit() {
        registerItem("entity_death_message_item", ENTITY_DEATH_MESSAGE_ITEM);

        INCUBUS_WOOD.registerRemaining(GROUP, GROUP, GROUP, GROUP);
        INCUBUS_WOOD.registerBoat(GROUP);
    }

    public static void clientInit() {
        INCUBUS_WOOD.registerClient();
    }
}
