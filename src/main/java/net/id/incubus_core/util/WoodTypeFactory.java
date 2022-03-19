package net.id.incubus_core.util;


import net.fabricmc.fabric.mixin.object.builder.AbstractBlockSettingsAccessor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.of;
import static net.minecraft.block.AbstractBlock.Settings.copy;
import static net.minecraft.block.Blocks.*;
import static net.minecraft.block.Blocks.OAK_WALL_SIGN;

/**
 * Takes tree colours and produces various block settings for that tree to ease the creation of wooden block sets
 * @param woodColor The color of the exposed wood of the log
 * @param barkColor The color of the bark of the log
 * @param leafColor The color of the leaves of the tree
 * @param plankColor The color of the wooden planks for this wood type
 */
@SuppressWarnings("unused")
public record WoodTypeFactory(MapColor woodColor, MapColor barkColor, MapColor leafColor,
                               MapColor plankColor) {

    private static final AbstractBlock.ContextPredicate never = (state, view, pos) -> false;
    private static final AbstractBlock.ContextPredicate always = (state, view, pos) -> true;

    /**
     * With this constructor, the plankColor is inferred to be the same as the woodColor.
     */
    public WoodTypeFactory(MapColor woodColor, MapColor barkColor, MapColor leafColor) {
        this(woodColor, barkColor, leafColor, woodColor);
    }

    /**
     * With this constructor, the plankColor is inferred to be the same as the woodColor,
     * and the leaf color is inferred to be dark green.
     */
    public WoodTypeFactory(MapColor woodColor, MapColor barkColor) {
        this(woodColor, barkColor, MapColor.DARK_GREEN, woodColor);
    }

    /**
     * @return A WoodTypeFactory that is the same as this one, but with a different specified leaf color.
     *         Note: This does not mutate this WoodTypeFactory, but rather makes a new one.
     */
    public WoodTypeFactory withLeafColor(MapColor color) {
        return new WoodTypeFactory(this.woodColor, this.barkColor, color, this.plankColor);
    }

    /**
     * @return The settings for a log block of this wood type.
     */
    public AbstractBlock.Settings log() {
        AbstractBlock.Settings log = copy(OAK_LOG);
        ((AbstractBlockSettingsAccessor) log).setMapColorProvider(state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? this.woodColor : this.barkColor);
        return log;
    }

    /**
     * @return The settings for a bark block of this wood type.
     */
    public AbstractBlock.Settings wood() {
        return copy(OAK_WOOD).mapColor(this.barkColor);
    }

    /**
     * @return The settings for a stripped log block of this wood type.
     */
    public AbstractBlock.Settings strippedLog() {
        return copy(STRIPPED_OAK_LOG).mapColor(this.woodColor);
    }

    /**
     * @return The settings for a stripped bark block of this wood type.
     */
    public AbstractBlock.Settings strippedWood() {
        return copy(STRIPPED_OAK_WOOD).mapColor(this.woodColor);
    }

    /**
     * @return The settings for a sapling of this wood type.
     */
    public AbstractBlock.Settings sapling() {
        return copy(OAK_SAPLING).mapColor(this.leafColor);
    }

    /**
     * @return The settings for a leaves block of this wood type.
     */
    public AbstractBlock.Settings leaves() {
        return copy(OAK_LEAVES).mapColor(this.leafColor);
    }

    /**
     * @return The settings for a leaves block of this wood type that can be walked through.
     */
    public AbstractBlock.Settings noCollideLeaves() {
        return this.leaves().noCollision().allowsSpawning((state, world, pos, type) -> false);
    }

    /**
     * @return The settings for a leaves block of this wood type that can be walked through
     * and glows.
     */
    public AbstractBlock.Settings auralNoCollideLeaves() {
        return aural(this.noCollideLeaves());
    }

    /**
     * @return The settings for a hanger block of this wood type. This is a decoration that droops below the leaves.
     */
    public AbstractBlock.Settings hanger() {
        return of(Material.DECORATION).strength(0.2f).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).suffocates(never).blockVision(never);
    }

    /**
     * @return The settings for a hanger block of this wood type that glows.
     */
    public AbstractBlock.Settings auralHanger() {
        return aural(this.hanger());
    }

    /**
     * @return The settings for a leaf pile block of this wood type.
     */
    public AbstractBlock.Settings leafPile() {
        return of(Material.REPLACEABLE_PLANT, this.leafColor).strength(0.2f).sounds(BlockSoundGroup.VINE).nonOpaque().suffocates(never).blockVision(never);
    }

    /**
     * @return The settings for a plank block of this wood type.
     */
    public AbstractBlock.Settings planks() {
        return copy(OAK_PLANKS).mapColor(this.plankColor);
    }

    /**
     * @return The settings for a trapdoor block of this wood type.
     */
    public AbstractBlock.Settings trapdoor() {
        return copy(OAK_TRAPDOOR).mapColor(this.plankColor);
    }

    /**
     * @return The settings for a door block of this wood type.
     */
    public AbstractBlock.Settings door() {
        return copy(OAK_DOOR).mapColor(this.plankColor);
    }

    /**
     * @return The settings for a button block of this wood type.
     */
    public AbstractBlock.Settings button() {
        return copy(OAK_BUTTON);
    }

    /**
     * @return The settings for a pressure plate block of this wood type.
     */
    public AbstractBlock.Settings pressurePlate() {
        return copy(OAK_BUTTON).mapColor(this.plankColor);
    }

    /**
     * @return The settings for a sign block of this wood type.
     */
    public AbstractBlock.Settings sign() {
        return copy(OAK_SIGN).mapColor(this.plankColor);
    }

    /**
     * @return The settings for a wall sign block of this wood type.
     */
    public AbstractBlock.Settings wallSign() {
        return copy(OAK_WALL_SIGN).mapColor(this.plankColor);
    }

    private static AbstractBlock.Settings aural(AbstractBlock.Settings settings) {
        return settings.strength(0.05f).luminance(state -> 12).emissiveLighting(always).postProcess(always);
    }
}