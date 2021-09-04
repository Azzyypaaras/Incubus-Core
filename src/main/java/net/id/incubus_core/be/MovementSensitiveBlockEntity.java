package net.id.incubus_core.be;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public interface MovementSensitiveBlockEntity {

    MovementSensitiveBlockEntity[] getObservers();

    void notifyObserver(BlockEntity movedEntity, BlockPos newPos);

    void notifyMoved(BlockPos newPos);
}
