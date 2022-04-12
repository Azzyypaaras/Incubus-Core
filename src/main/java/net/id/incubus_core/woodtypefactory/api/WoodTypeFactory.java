package net.id.incubus_core.woodtypefactory.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.id.incubus_core.woodtypefactory.api.boat.BoatFactory;
import net.id.incubus_core.woodtypefactory.api.chest.ChestFactory;
import net.id.incubus_core.woodtypefactory.api.sign.SignFactory;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A factory for all things wood.
 * <br>The default constructor isn't recommended. Instead, use the ::of method,
 * which does eveything for you
 * @author Jack Papel
 */
@SuppressWarnings("unused")
public record WoodTypeFactory(@NotNull String modId,
                              @NotNull String woodName,
                              @Nullable SaplingBlock sapling,
                              @Nullable FlowerPotBlock pottedSapling,
                              PillarBlock log,
                              PillarBlock wood,
                              PillarBlock strippedLog,
                              PillarBlock strippedWood,
                              @Nullable LeavesBlock leaves,
                              Block planks,
                              FenceBlock fence,
                              FenceGateBlock fenceGate,
                              SlabBlock slab,
                              StairsBlock stairs,
                              TrapdoorBlock trapdoor,
                              DoorBlock door,
                              WoodenButtonBlock button,
                              PressurePlateBlock pressurePlate,
                              SignFactory signFactory,
                              @Nullable ChestFactory chestFactory
) {

    /**
     * Creates a new WoodTypeFactory with the given parameters
     * @param settings The WoodSettingsFactory associated with this kind of wood.
     * @param modId The id of your mod, obviously.
     * @param woodName The id of this wood, before any affixes.
     *                 For example: The woodName for "birch_planks" and "birch_stairs" is "birch"
     * @param saplingGenerator The sapling generator for this wood's tree.
     * @param hasChest Whether to create a new chest type for this kind of wood.
     */
    public static WoodTypeFactory of(WoodSettingsFactory settings, String modId, String woodName,
                                     @Nullable SaplingGenerator saplingGenerator, boolean hasChest,
                                     boolean hasLeaves) {
        boolean hasSapling = saplingGenerator != null;
        SaplingBlock sapling = new IncubusSaplingBlock(saplingGenerator, settings.sapling());

        Block planks = new Block(settings.planks());

        return new WoodTypeFactory(
                modId,
                woodName,
                hasSapling ? sapling : null,
                hasSapling ? new FlowerPotBlock(sapling, AbstractBlock.Settings.copy(Blocks.POTTED_OAK_SAPLING)) : null,
                new PillarBlock(settings.log()),
                new PillarBlock(settings.wood()),
                new PillarBlock(settings.strippedLog()),
                new PillarBlock(settings.strippedWood()),
                hasLeaves ? new LeavesBlock(settings.leaves()) : null,
                planks,
                new FenceBlock(settings.planks()),
                new FenceGateBlock(settings.planks()),
                new SlabBlock(settings.planks()),
                new IncubusStairsBlock(planks.getDefaultState(), settings.planks()),
                new IncubusTrapdoorBlock(settings.planks()),
                new IncubusDoorBlock(settings.door()),
                new IncubusWoodenButtonBlock(settings.button()),
                new IncubusWoodenPressurePlateBlock(settings.pressurePlate()),
                new SignFactory(modId, woodName, settings.sign(), settings.wallSign()),
                hasChest ? new ChestFactory(modId, woodName, settings.chest()) : null
        );
    }

    /**
     * Creates a new WoodTypeFactory with the given parameters, without a chest, a sapling block, or leaves
     * @param settings The WoodSettingsFactory associated with this kind of wood.
     * @param modId The id of your mod, obviously.
     * @param woodName The id of this wood, before any affixes.
     *                 For example: The woodName for "birch_planks" and "birch_stairs" is "birch"
     */
    public static WoodTypeFactory of(WoodSettingsFactory settings, String modId, String woodName) {
        return of(settings, modId, woodName, null, false, false);
    }

    /**
     * Registers any remaining blocks for this wood that have not been registered yet.
     * <br>Note: Will not overwrite existing registered blocks, and will not break if there are any.
     */
    public void registerRemainingBlocks() {
        this.registerBlockSafely(this.sapling, this.woodName + "_sapling");
        this.registerBlockSafely(this.pottedSapling, "potted_" + this.woodName + "_sapling");
        this.registerBlockSafely(this.log, this.woodName + "_log");
        this.registerBlockSafely(this.wood, this.woodName + "_wood");
        this.registerBlockSafely(this.strippedLog, "stripped_" + this.woodName + "_log");
        this.registerBlockSafely(this.strippedWood, "stripped_" + this.woodName + "_wood");
        this.registerBlockSafely(this.leaves, this.woodName + "_leaves");
        this.registerBlockSafely(this.planks, this.woodName + "_planks");
        this.registerBlockSafely(this.fence, this.woodName + "_fence");
        this.registerBlockSafely(this.fenceGate, this.woodName + "_fence_gate");
        this.registerBlockSafely(this.slab, this.woodName + "_slab");
        this.registerBlockSafely(this.stairs, this.woodName + "_stairs");
        this.registerBlockSafely(this.trapdoor, this.woodName + "_trapdoor");
        this.registerBlockSafely(this.door, this.woodName + "_door");
        this.registerBlockSafely(this.button, this.woodName + "_button");
        this.registerBlockSafely(this.pressurePlate, this.woodName + "_pressure_plate");
        this.registerBlockSafely(this.signFactory.signBlock, this.woodName + "_sign");
        this.registerBlockSafely(this.signFactory.wallSignBlock, this.woodName + "_wall_sign");
        if (this.chestFactory != null) {
            this.registerBlockSafely(this.chestFactory.chest, this.chestFactory.chestName + "_chest");
        }
    }

    /**
     * Registers any remaining items for this wood that have not been registered yet.
     * <br>Note: Will not overwrite existing registered blocks, and will not break if there are any.
     * <br>Note: This does not register a boat item
     */
    public void registerRemainingItems(ItemGroup blocks, ItemGroup decorations, ItemGroup doors, ItemGroup buttonsAndPressurePlates) {
        Item.Settings decorGroup = new Item.Settings().group(decorations);
        Item.Settings blocksGroup = new Item.Settings().group(blocks);
        this.registerBlockItemSafely(this.sapling, this.woodName + "_sapling", decorGroup);
        this.registerBlockItemSafely(this.log, this.woodName + "_log", blocksGroup);
        this.registerBlockItemSafely(this.wood, this.woodName + "_wood", blocksGroup);
        this.registerBlockItemSafely(this.strippedLog, "stripped_" + this.woodName + "_log", blocksGroup);
        this.registerBlockItemSafely(this.strippedWood, "stripped_" + this.woodName + "_wood", blocksGroup);
        this.registerBlockItemSafely(this.leaves, this.woodName + "_leaves", decorGroup);
        this.registerBlockItemSafely(this.planks, this.woodName + "_planks", blocksGroup);
        this.registerBlockItemSafely(this.fence, this.woodName + "_fence", decorGroup);
        this.registerBlockItemSafely(this.fenceGate, this.woodName + "_fence_gate", decorGroup);
        this.registerBlockItemSafely(this.slab, this.woodName + "_slab", blocksGroup);
        this.registerBlockItemSafely(this.stairs, this.woodName + "_stairs", blocksGroup);
        this.registerBlockItemSafely(this.trapdoor, this.woodName + "_trapdoor", new Item.Settings().group(doors));
        this.registerBlockItemSafely(this.door, this.woodName + "_door", new Item.Settings().group(doors));
        this.registerBlockItemSafely(this.button, this.woodName + "_button", new Item.Settings().group(buttonsAndPressurePlates));
        this.registerBlockItemSafely(this.pressurePlate, this.woodName + "_pressure_plate", new Item.Settings().group(buttonsAndPressurePlates));
        this.registerItemSafely(new SignItem(new Item.Settings().group(decorations).maxCount(16), this.signFactory.signBlock, this.signFactory.wallSignBlock), this.woodName + "_sign");
        if (this.chestFactory != null) {
            this.registerBlockItemSafely(this.chestFactory.chest, this.chestFactory.chestName + "_chest", new Item.Settings().group(decorations));
        }
    }

    /**
     * Registers any remaining blocks and items for this wood that have not been registered yet.
     * <br>Note: Will not overwrite existing registered blocks, and will not break if there are any.
     * <br>Note: This does not register a boat
     */
    public void registerRemaining(ItemGroup blocks, ItemGroup decorations, ItemGroup doors, ItemGroup buttonsAndPressurePlates) {
        registerRemainingBlocks();
        registerRemainingItems(blocks, decorations, doors, buttonsAndPressurePlates);
    }

    private void registerBlockSafely(Block block, String id) {
        if (block == null) return; // Nope.

        Identifier identifier = new Identifier(this.modId, id);
        if (Registry.BLOCK.containsId(identifier)) return; // Nice try.

        Registry.register(Registry.BLOCK, identifier, block);
    }

    private void registerItemSafely(Item item, String id) {
        if (item == null) return; // Nope.

        Identifier identifier = new Identifier(this.modId, id);
        if (Registry.ITEM.containsId(identifier)) return; // Nice try.

        Registry.register(Registry.ITEM, identifier, item);
    }

    private void registerBlockItemSafely(Block block, String id, Item.Settings settings) {
        if (block == null) return; // Nope.

        Identifier identifier = new Identifier(this.modId, id);
        if (Registry.ITEM.containsId(identifier)) return; // Nice try.

        Registry.register(Registry.ITEM, identifier, new BlockItem(block, settings));
    }

    /**
     * Must be called in order for things to render properly
     */
    @Environment(EnvType.CLIENT)
    public void registerClient() {
        if (this.chestFactory != null) {
            this.chestFactory.registerChestRenderers();
        }
        if (this.signFactory != null) {
            this.signFactory.registerSignRenderers();
        }
    }

    /**
     * Note: Don't call this method or its overloaded methods twice, or else it registers the boat and item twice,
     * which it cannot do.
     * @return The boat factory for this wood type.
     */
    public BoatFactory registerBoat(Item.Settings settings) {
        return new BoatFactory(settings, this.planks(), this.modId, this.woodName);
    }

    /**
     * Note: Don't call this method or its overloaded methods twice, or else it registers the boat and item twice,
     * which it cannot do.
     * @return The boat factory for this wood type.
     */
    @SuppressWarnings("UnusedReturnValue")
    public BoatFactory registerBoat(ItemGroup group) {
        return registerBoat(new Item.Settings().group(group).maxCount(16));
    }

    /*
    Various classes solely because Mojang makes things protected for no reason.
     */
    public static class IncubusSaplingBlock extends SaplingBlock {
        public IncubusSaplingBlock(SaplingGenerator generator, Settings settings) {
            super(generator, settings);
        }
    }
    public static class IncubusStairsBlock extends StairsBlock {
        public IncubusStairsBlock(BlockState baseBlockState, Settings settings) {
            super(baseBlockState, settings);
        }
    }
    public static class IncubusTrapdoorBlock extends TrapdoorBlock {
        public IncubusTrapdoorBlock(Settings settings) {
            super(settings);
        }
    }
    public static class IncubusDoorBlock extends DoorBlock {
        public IncubusDoorBlock(Settings settings) {
            super(settings);
        }
    }
    public static class IncubusWoodenButtonBlock extends WoodenButtonBlock {
        public IncubusWoodenButtonBlock(Settings settings) {
            super(settings);
        }
    }
    public static class IncubusWoodenPressurePlateBlock extends PressurePlateBlock {
        public IncubusWoodenPressurePlateBlock(Settings settings) {
            super(ActivationRule.EVERYTHING, settings);
        }
    }
}
