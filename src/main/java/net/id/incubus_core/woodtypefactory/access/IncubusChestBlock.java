package net.id.incubus_core.woodtypefactory.access;

import net.id.incubus_core.mixin.woodtypefactory.chest.ChestBlockEntityAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;

import java.util.function.Supplier;

public class IncubusChestBlock extends ChestBlock{
    public IncubusChestBlock(Settings settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> typeSupplier){
        super(settings, typeSupplier);
    }
    
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state){
        return ChestBlockEntityAccessor.init(entityTypeRetriever.get(), pos, state);
    }
}
