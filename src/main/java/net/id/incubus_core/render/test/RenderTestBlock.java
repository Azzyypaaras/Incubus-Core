package net.id.incubus_core.render.test;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class RenderTestBlock extends AbstractGlassBlock {
    public RenderTestBlock(Settings settings) {
        super(settings);
    }
}
