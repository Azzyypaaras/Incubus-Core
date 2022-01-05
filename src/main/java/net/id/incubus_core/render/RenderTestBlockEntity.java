package net.id.incubus_core.render;

import net.id.incubus_core.IncubusCore;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class RenderTestBlockEntity extends BlockEntity {

    public RenderTestBlockEntity(BlockPos pos, BlockState state) {
        super(IncubusCore.RENDER_TEST_BLOCK_ENTITY_TYPE, pos, state);
    }
}
