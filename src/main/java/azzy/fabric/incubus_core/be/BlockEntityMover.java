package azzy.fabric.incubus_core.be;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Arrays;

public class BlockEntityMover {

    public static void tryMoveEntity(World world, BlockPos pos, Direction facing) {
        directEntityMove(world, pos, pos.offset(facing));
    }

    public static void directEntityMove(World world, BlockPos pos, BlockPos newPos) {
        if(!world.isClient()) {
            BlockState state = world.getBlockState(pos);
            BlockEntity entity = world.getBlockEntity(pos);
            if(entity != null && !(entity instanceof PistonBlockEntity)) {
                CompoundTag tag = new CompoundTag();
                entity.toTag(tag);
                world.removeBlockEntity(pos);
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.setBlockState(newPos, state);
                BlockEntity newEntity = entity.getType().get(world, newPos);
                if(newEntity != null) {
                    newEntity.fromTag(world.getBlockState(newPos), tag);
                    world.setBlockEntity(newPos, newEntity);
                    if(newEntity instanceof BlockEntityClientSerializable)
                        ((BlockEntityClientSerializable) entity).sync();
                    if(newEntity instanceof MovementSensitiveBlockEntity) {
                        MovementSensitiveBlockEntity sensitiveEntity = (MovementSensitiveBlockEntity) entity;
                        Arrays.stream(sensitiveEntity.getObservers()).forEach(observer -> observer.notifyObserver(newEntity, newPos));
                        sensitiveEntity.notifyMoved(newPos);
                    }
                }
            }
        }
    }
}
