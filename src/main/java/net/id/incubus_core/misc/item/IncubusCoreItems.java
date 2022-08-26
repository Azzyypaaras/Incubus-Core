package net.id.incubus_core.misc.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.id.incubus_core.misc.IncubusSounds;
import net.id.incubus_core.misc.IncubusToolMaterials;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

import static net.id.incubus_core.IncubusCore.registerItem;

public class IncubusCoreItems {


    public static final LunarianSaberItem LUNARIAN_SABER_ITEM = new LunarianSaberItem(IncubusToolMaterials.LUNARIAN, 0, -2.25F, new FabricItemSettings().fireproof());
    public static final AzzysFlagItem AZZYS_ELEMENTAL_FLAG_ITEM = new AzzysFlagItem(IncubusToolMaterials.LUNARIAN, -2, -1F, new FabricItemSettings());
    public static final LongSpatulaItem LONG_SPATULA = new LongSpatulaItem(IncubusToolMaterials.MILD_STEEL, 2, -2.5F, new FabricItemSettings().fireproof());

    public static final FoxEffigyItem FOX_EFFIGY = new FoxEffigyItem(new FabricItemSettings().maxCount(1));
    public static final BerryBranchItem BERRY_BRANCH = new BerryBranchItem(new FabricItemSettings().maxCount(1).food(IncubusFoodComponents.BERRY_BRANCH));

    public static final IncubusMusicDiscItem DUPED_SHOVELS = new IncubusMusicDiscItem(0, IncubusSounds.DUPED_SHOVELS, new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.RARE));
    public static final IncubusMusicDiscItem VINESUS = new IncubusMusicDiscItem(0, IncubusSounds.VINESUS, new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.RARE));
    public static final IncubusMusicDiscItem DECLINE = new IncubusMusicDiscItem(0, IncubusSounds.DECLINE, new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.EPIC));
    public static final IncubusMusicDiscItem RIPPLE = new IncubusMusicDiscItem(0, IncubusSounds.COSMIC_OCEAN, new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.EPIC));

    public static final Item MOBILK_1 = new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON).food(IncubusFoodComponents.MOBILK_1));
    public static final Item LEAN = new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON).food(IncubusFoodComponents.LEAN));
    public static final Item RAT_POISON = new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON).food(IncubusFoodComponents.RAT_POISON));

    public static final Item DEBUG_FLAME_ITEM = new Item(new FabricItemSettings().fireproof().rarity(Rarity.EPIC).maxCount(1).equipmentSlot(stack -> EquipmentSlot.HEAD));

    public static void init() {
        registerItem("lunarian_saber", LUNARIAN_SABER_ITEM);
        registerItem("lord_azzys_elemental_flag", AZZYS_ELEMENTAL_FLAG_ITEM);
        registerItem("long_spatula", LONG_SPATULA);
        registerItem("fox_effigy", FOX_EFFIGY);
        registerItem("everfruiting_berry_branch", BERRY_BRANCH);
        registerItem("debug_flame", DEBUG_FLAME_ITEM);
        registerItem("sacred_disc_1", DUPED_SHOVELS);
        registerItem("sacred_disc_2", VINESUS);
        registerItem("legend_1", DECLINE);
        registerItem("legend_2", RIPPLE);
        registerItem("mobilk-1", MOBILK_1);
        registerItem("lean", LEAN);
        registerItem("rat_poison", RAT_POISON);

    }
}
