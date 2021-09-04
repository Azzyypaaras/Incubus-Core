package net.id.incubus_core.misc;

import net.id.incubus_core.be.BlockEntityMover;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class HandPistonItem extends Item {

    private final boolean moveAll;

    public HandPistonItem(Settings settings, boolean moveAll) {
        super(settings);
        this.moveAll = moveAll;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        Direction moveDir = context.getSide().getOpposite();
        PistonBehavior behavior = world.getBlockState(pos).getPistonBehavior();
        switch (behavior) {
            case DESTROY: world.breakBlock(pos, true); break;
            case BLOCK: {
                if(!moveAll)
                    break;
            }
            case PUSH_ONLY:
            case NORMAL: {
                if(!world.isClient()) {

                    BlockState state = world.getBlockState(pos);

                    if(!PistonBlock.isMovable(state, world, pos, moveDir, false, moveDir.getOpposite()) || (state.isOf(Blocks.OBSIDIAN) || state.isOf(Blocks.CRYING_OBSIDIAN) || state.isOf(Blocks.RESPAWN_ANCHOR)))
                        if(!moveAll || state.getHardness(world, pos) < 0f)
                            break;;

                    BlockPos offPos = pos.offset(moveDir);

                    if(!world.getBlockState(offPos).getMaterial().isReplaceable())
                        break;

                    world.breakBlock(offPos, true);

                    if(world.getBlockEntity(pos) != null) {
                        BlockEntityMover.tryMoveEntity((ServerWorld) world, pos, moveDir);
                    }
                    else {
                        world.removeBlock(pos, true);
                        world.setBlockState(offPos, state);
                    }
                    world.playSound(null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 1, moveAll ? 1.4F : 2);
                }
                return ActionResult.success(world.isClient());
            }
        }
        return super.useOnBlock(context);
    }
}
