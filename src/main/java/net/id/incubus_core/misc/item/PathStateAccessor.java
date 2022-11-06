package net.id.incubus_core.misc.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

import java.util.Map;

public abstract class PathStateAccessor extends ShovelItem {

    private PathStateAccessor(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public static Map<Block, BlockState> getPathStates() {
        return PATH_STATES;
    }
}
