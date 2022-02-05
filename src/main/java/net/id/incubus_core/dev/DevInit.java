package net.id.incubus_core.dev;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.IncubusCore;
import net.id.incubus_core.dev.item.EntityDeathMessageTestItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import static net.id.incubus_core.IncubusCore.registerItem;
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
    
    public static void commonInit() {
        registerItem("entity_death_message_item", ENTITY_DEATH_MESSAGE_ITEM);
    }
}
