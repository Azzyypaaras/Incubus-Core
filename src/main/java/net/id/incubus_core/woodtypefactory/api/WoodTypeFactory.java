package net.id.incubus_core.woodtypefactory.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.id.incubus_core.woodtypefactory.api.boat.BoatFactory;
import net.id.incubus_core.woodtypefactory.api.chest.ChestFactory;
import net.id.incubus_core.woodtypefactory.api.sign.SignFactory;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * A factory for all things wood.
 * <br>This class will create all the blocks and items for you, as
 * long as you specify what blocks and items you want.
 * Example:
 * <pre>
 * // We want to create a wood type called incubus wood.
 * private static final WoodTypeFactory INCUBUS =
 *     new WoodTypeFactory(
 *         new WoodSettingsFactory(MapColor.BLACK, MapColor.BRIGHT_RED),
 *         "incubus_core", "incubus"
 *     );
 *
 * public static final PillarBlock INCUBUS_LOG = INCUBUS.log();
 * public static final Block INCUBUS_PLANKS = INCUBUS.planks();
 * public static final ChestBlock INCUBUS_CHEST = INCUBUS.chestFactory().chest;
 * public static final Item INCUBUS_BOAT = INCUBUS.boatFactory(GROUP).item;
 * ... etc.
 *
 * ... later on ...
 * public static void init() {
 *     INCUBUS.registerRemaining();
 *     INCUBUS.registerStrippable();
 *     INCUBUS.registerFlammable();
 * }
 *
 * public static void initClient() {
 *     INCUBUS.registerClient();
 *     INCUBUS.registerRenderLayers();
 * }
 * </pre>
 *
 * @author Jack Papel
 */
@SuppressWarnings("unused")
public final class WoodTypeFactory {
    public final @NotNull String modId;
    public final @NotNull String woodName;
    public final @NotNull WoodSettingsFactory settings;
    private final @Nullable SaplingGenerator saplingGenerator;
    private SaplingBlock sapling;
    private FlowerPotBlock pottedSapling;
    private PillarBlock log;
    private PillarBlock wood;
    private PillarBlock strippedLog;
    private PillarBlock strippedWood;
    private LeavesBlock leaves;
    private Block planks;
    private FenceBlock fence;
    private FenceGateBlock fenceGate;
    private SlabBlock slab;
    private StairsBlock stairs;
    private TrapdoorBlock trapdoor;
    private DoorBlock door;
    private WoodenButtonBlock button;
    private PressurePlateBlock pressurePlate;
    private SignFactory signFactory;
    private ChestFactory chestFactory;
    private BoatFactory boatFactory;

    /**
     * Creates a new WoodTypeFactory with the given parameters
     * @param settings The WoodSettingsFactory associated with this kind of wood.
     * @param modId The id of your mod, obviously.
     * @param woodName The id of this wood, before any affixes.
     *                 For example: The woodName for "birch_planks" and "birch_stairs" is "birch"
     * @param saplingGenerator The sapling generator for this wood's tree.
     */
    public WoodTypeFactory(@NotNull WoodSettingsFactory settings, @NotNull String modId, @NotNull String woodName,
                           @Nullable SaplingGenerator saplingGenerator) {
        this.settings = settings;
        this.modId = modId;
        this.woodName = woodName;
        this.saplingGenerator = saplingGenerator;
    }

    /**
     * Creates a new WoodTypeFactory with the given parameters
     * @param settings The WoodSettingsFactory associated with this kind of wood.
     * @param woodId The identifier of this wood, before any affixes.
     *                 For example: The woodId for "minecraft:birch_planks" and "minecraft:birch_stairs" is "minecraft:birch"
     * @param saplingGenerator The sapling generator for this wood's tree.
     */
    public WoodTypeFactory(@NotNull WoodSettingsFactory settings, @NotNull Identifier woodId,
                           @Nullable SaplingGenerator saplingGenerator) {
        this(settings, woodId.getNamespace(), woodId.getPath(), saplingGenerator);
    }

    /**
     * Creates a new WoodTypeFactory with the given parameters, without a sapling generator.
     * <br>Note: Using this constructor, you cannot use the ::sapling() or ::pottedSapling() methods.
     * @param settings The WoodSettingsFactory associated with this kind of wood.
     * @param modId The id of your mod, obviously.
     * @param woodName The id of this wood, before any affixes.
     *                 For example: The woodName for "birch_planks" and "birch_stairs" is "birch"
     */
    public WoodTypeFactory(@NotNull WoodSettingsFactory settings, String modId, String woodName) {
        this(settings, modId, woodName, null);
    }

    /**
     * Creates a new WoodTypeFactory with the given parameters
     * <br>Note: Using this constructor, you cannot use the ::sapling() or ::pottedSapling() methods.
     * @param settings The WoodSettingsFactory associated with this kind of wood.
     * @param woodId The identifier of this wood, before any affixes.
     *                 For example: The woodId for "minecraft:birch_planks" and "minecraft:birch_stairs" is "minecraft:birch"
     */
    public WoodTypeFactory(@NotNull WoodSettingsFactory settings, @NotNull Identifier woodId) {
        this(settings, woodId.getNamespace(), woodId.getPath());
    }

    /**
     * Registers any blocks for this wood type that have been created but not registered.
     * <br>Note: Will not overwrite existing registered blocks, and will not break if there are any.
     * <br>Note: Does not register any items.
     * <br>Note: Does not register flammability, stripping, nor render layers.
     * @see #registerCreatedItems(ItemGroup, ItemGroup, ItemGroup, ItemGroup)
     * @see #registerFlammability()
     * @see #registerStripping()
     * @see #registerRenderLayers()
     */
    public void registerCreatedBlocks() {
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
        if (this.signFactory != null) {
            this.registerBlockSafely(this.signFactory.signBlock, this.woodName + "_sign");
            this.registerBlockSafely(this.signFactory.wallSignBlock, this.woodName + "_wall_sign");
        }
        if (this.chestFactory != null) {
            this.registerBlockSafely(this.chestFactory.chest, this.chestFactory.chestName + "_chest");
        }
    }

    /**
     * Registers any remaining items for this wood that have been created but not registered.
     * <br>Note: Will not overwrite existing registered items, and will not break if there are any.
     * <br>Note: Will not register items for blocks that have not been registered.
     * <br>Note: Will not register a boat item.
     * <br>Note: Will not register any blocks.
     * <br>Note: Does not register flammability, stripping, nor render layers.
     * @see #registerCreatedBlocks()
     * @see #boatFactory(Item.Settings)
     * @see #registerFlammability()
     * @see #registerStripping()
     * @see #registerRenderLayers()
     */
    public void registerCreatedItems(ItemGroup blocks, ItemGroup decorations, ItemGroup doors, ItemGroup buttonsAndPressurePlates) {
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
        if (this.signFactory != null) {
            this.registerItemSafely(new SignItem(new Item.Settings().group(decorations).maxCount(16), this.signFactory.signBlock, this.signFactory.wallSignBlock), this.woodName + "_sign");
        }
        if (this.chestFactory != null) {
            this.registerBlockItemSafely(this.chestFactory.chest, this.chestFactory.chestName + "_chest", new Item.Settings().group(decorations));
        }
    }

    /**
     * Registers blocks to FAPI's FlammableBlockRegistry.
     */
    public void registerFlammability() {
        // Burns like logs
        registerFlammabilitySafely(this.log, 5, 5);
        registerFlammabilitySafely(this.wood, 5, 5);
        registerFlammabilitySafely(this.strippedLog, 5, 5);
        registerFlammabilitySafely(this.strippedWood, 5, 5);
        // Burns like leaves
        registerFlammabilitySafely(this.leaves, 60, 30);
        // Burns like planks
        registerFlammabilitySafely(this.planks, 20, 5);
        registerFlammabilitySafely(this.fence, 20, 5);
        registerFlammabilitySafely(this.fenceGate, 20, 5);
        registerFlammabilitySafely(this.slab, 20, 5);
        registerFlammabilitySafely(this.stairs, 20, 5);
    }

    private void registerFlammabilitySafely(Block block, int burn, int spread) {
        if (block == null) return;

        FlammableBlockRegistry.getDefaultInstance().add(block, burn, spread);
    }

    /**
     * Registers blocks to FAPI's StrippableBlockRegistry
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void registerStripping() {
        if (this.log != null && this.strippedLog != null) {
            StrippableBlockRegistry.register(this.log, this.strippedLog);
        }
        if (this.wood != null & this.strippedWood != null) {
            StrippableBlockRegistry.register(this.wood, this.strippedWood);
        }
    }

    /**
     * Puts blocks on their correct render layer.
     * <br>Note: Only call this method client-side.
     */
    @Environment(EnvType.CLIENT)
    public void registerRenderLayers() {
        RenderLayer cutout = RenderLayer.getCutout();
        registerRenderLayerSafely(this.sapling, cutout);
        registerRenderLayerSafely(this.pottedSapling, cutout);
        registerRenderLayerSafely(this.leaves, RenderLayer.getCutoutMipped());
        registerRenderLayerSafely(this.trapdoor, cutout);
        registerRenderLayerSafely(this.door, cutout);
    }

    @Environment(EnvType.CLIENT)
    private void registerRenderLayerSafely(Block block, RenderLayer layer) {
        if (block == null) return;

        BlockRenderLayerMap.INSTANCE.putBlock(block, layer);
    }

    /**
     * Registers any remaining blocks and items for this wood that have been created but not registered.
     * <br>Note: Will not overwrite existing registered blocks, and will not break if there are any.
     * <br>Note: Will not register a boat.
     * <br>Note: Does not register flammability, stripping, nor render layers.
     * @see #boatFactory(Item.Settings)
     * @see #registerFlammability()
     * @see #registerStripping()
     * @see #registerRenderLayers()
     */
    public void registerCreatedBlocksAndItems(ItemGroup blocks, ItemGroup decorations, ItemGroup doors, ItemGroup buttonsAndPressurePlates) {
        registerCreatedBlocks();
        registerCreatedItems(blocks, decorations, doors, buttonsAndPressurePlates);
    }

    private void registerBlockSafely(Block block, String id) {
        if (block == null) return; // Nope.

        Identifier identifier = new Identifier(modId, id);
        if (Registry.BLOCK.containsId(identifier)) return;

        Registry.register(Registry.BLOCK, identifier, block);
    }

    private void registerItemSafely(Item item, String id) {
        if (item == null) return; // Nope.

        Identifier identifier = new Identifier(modId, id);
        if (Registry.ITEM.containsId(identifier)) return;

        Registry.register(Registry.ITEM, identifier, item);
    }

    private void registerBlockItemSafely(Block block, String id, Item.Settings settings) {
        if (block == null) return; // Nope.

        Identifier identifier = new Identifier(this.modId, id);
        if (Registry.ITEM.containsId(identifier)) return;

        if (!Registry.BLOCK.containsId(identifier)) {
            throw new RuntimeException(this + ": Register your blocks before your items! Perpetrator: " + identifier);
        }

        Registry.register(Registry.ITEM, identifier, new BlockItem(block, settings));
    }

    /**
     * Must be called in order for block entities to render properly
     * <br>Note: Only call this method client-side.
     * <br>Note: Will not register render layers.
     * @see #registerRenderLayers()
     */
    @Environment(EnvType.CLIENT)
    public void registerBlockEntityRenderers() {
        if (this.chestFactory != null) {
            this.chestFactory.registerChestRenderers();
        }
        if (this.signFactory != null) {
            this.signFactory.registerSignRenderers();
        }
    }

    public BoatFactory boatFactory(Item.Settings settings) {
        if (this.boatFactory != null) return boatFactory;
        if (this.planks == null) throw new IllegalStateException("Create a planks block for " + this + " before creating a boat!");

        return new BoatFactory(settings, this.planks(), this.modId, this.woodName);
    }

    public BoatFactory boatFactory(ItemGroup group) {
        return boatFactory(new Item.Settings().group(group).maxCount(16));
    }

    /**
     * Can only be called if a saplingGenerator was specified in {@link #WoodTypeFactory(WoodSettingsFactory, String, String, SaplingGenerator) the constructor}.
     */
    public SaplingBlock sapling() {
        if (this.sapling != null) return sapling;
        if (this.saplingGenerator == null) {
            throw new IllegalStateException("Specify a sapling generator for " + this + " if you want a sapling!");
        }

        return this.sapling = new IncubusSaplingBlock(this.saplingGenerator, this.settings.sapling());
    }

    /**
     * Can only be called after {@link #sapling()}
     */
    public FlowerPotBlock pottedSapling() {
        if (this.pottedSapling != null) return pottedSapling;
        if (this.sapling == null) {
            throw new IllegalStateException("Create a sapling for " + this + " before creating a potted sapling!");
        }

        return this.pottedSapling = new FlowerPotBlock(this.sapling, this.settings.pottedSapling());
    }

    public PillarBlock log() {
        if (this.log != null) return log;
        return this.log = new PillarBlock(this.settings.log());
    }

    public PillarBlock wood() {
        if (this.wood != null) return wood;
        return this.wood = new PillarBlock(this.settings.wood());
    }

    public PillarBlock strippedLog() {
        if (this.strippedLog != null) return strippedLog;
        return this.strippedLog = new PillarBlock(this.settings.strippedLog());
    }

    public PillarBlock strippedWood() {
        if (this.strippedWood != null) return strippedWood;
        return this.strippedWood = new PillarBlock(this.settings.strippedWood());
    }

    public LeavesBlock leaves() {
        if (this.leaves != null) return leaves;
        return this.leaves = new LeavesBlock(this.settings.leaves());
    }

    public Block planks() {
        if (this.planks != null) return planks;
        return this.planks = new Block(this.settings.planks());
    }

    public FenceBlock fence() {
        if (this.fence != null) return fence;
        return this.fence = new FenceBlock(this.settings.planks());
    }

    public FenceGateBlock fenceGate() {
        if (this.fenceGate != null) return fenceGate;
        return this.fenceGate = new FenceGateBlock(this.settings.planks());
    }

    public SlabBlock slab() {
        if (this.slab != null) return slab;
        return this.slab = new SlabBlock(this.settings.planks());
    }

    /**
     * Can only be called after {@link #planks()}
     */
    public StairsBlock stairs() {
        if (this.stairs != null) return stairs;
        if (this.planks == null) {
            throw new IllegalStateException("Create planks for " + this + " first if you want stairs!");
        }
        return this.stairs = new IncubusStairsBlock(this.planks.getDefaultState(), this.settings.planks());
    }

    public TrapdoorBlock trapdoor() {
        if (this.trapdoor != null) return trapdoor;
        return this.trapdoor = new IncubusTrapdoorBlock(this.settings.trapdoor());
    }

    public DoorBlock door() {
        if (this.door != null) return door;
        return this.door = new IncubusDoorBlock(this.settings.door());
    }

    public WoodenButtonBlock button() {
        if (this.button != null) return button;
        return this.button = new IncubusWoodenButtonBlock(this.settings.button());
    }

    public PressurePlateBlock pressurePlate() {
        if (this.pressurePlate != null) return pressurePlate;
        return this.pressurePlate = new IncubusWoodenPressurePlateBlock(this.settings.pressurePlate());
    }

    public SignFactory signFactory() {
        if (this.signFactory != null) return signFactory;
        return this.signFactory = new SignFactory(modId, woodName, settings.sign(), settings.wallSign());
    }

    public ChestFactory chestFactory() {
        if (this.chestFactory != null) return chestFactory;
        return this.chestFactory = new ChestFactory(modId, woodName, settings.chest());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (WoodTypeFactory) obj;
        return Objects.equals(this.modId, that.modId) &&
                Objects.equals(this.woodName, that.woodName) &&
                Objects.equals(this.saplingGenerator, that.saplingGenerator) &&
                Objects.equals(this.settings, that.settings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modId, woodName, saplingGenerator, settings);
    }

    @Override
    public String toString() {
        return "WoodTypeFactory[" + modId + ":" + woodName +"]";
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
