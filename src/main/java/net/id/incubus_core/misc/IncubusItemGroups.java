package net.id.incubus_core.misc;

import net.id.incubus_core.IncubusCoreCommon;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static net.id.incubus_core.IncubusCoreCommon.MODID;

public class IncubusItemGroups {
    public static final ItemGroup CORE_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, "core_group"), () -> new ItemStack(IncubusCoreCommon.LUNARIAN_SABER));
}
