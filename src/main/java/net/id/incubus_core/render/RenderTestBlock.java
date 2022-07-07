package net.id.incubus_core.render;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class RenderTestBlock extends AbstractGlassBlock implements BlockEntityProvider {

    public static final BooleanProperty SOFT = BooleanProperty.of("soft");

    public RenderTestBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RenderTestBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        if(ctx.shouldCancelInteraction())
            return getDefaultState().with(SOFT, false);
        return super.getPlacementState(ctx);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SOFT);
    }
}
