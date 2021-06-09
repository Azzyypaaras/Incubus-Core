package azzy.fabric.incubus_core.misc;

import azzy.fabric.incubus_core.be.BlockEntityMover;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
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
            case DESTROY: world.breakBlock(pos, true);
            case BLOCK: {
                if(!moveAll)
                    break;
            }
            case PUSH_ONLY:
            case NORMAL: {
                if(!world.isClient()) {

                    BlockPos offPos = pos.offset(moveDir);

                    if(!world.getBlockState(offPos).getMaterial().isReplaceable())
                        break;

                    world.breakBlock(offPos, true);

                    if(world.getBlockEntity(pos) != null) {
                        BlockEntityMover.tryMoveEntity((ServerWorld) world, pos, moveDir);
                    }
                    else {
                        BlockState state = world.getBlockState(pos);
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
