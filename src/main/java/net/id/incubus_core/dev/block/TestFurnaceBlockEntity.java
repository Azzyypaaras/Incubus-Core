package net.id.incubus_core.dev.block;

import net.id.incubus_core.dev.DevInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class TestFurnaceBlockEntity extends AbstractFurnaceBlockEntity {

    public TestFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(DevInit.TEST_FURNACE_BLOCK_ENTITY_TYPE, pos, state, DevInit.TEST_RECIPE_TYPE);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.furnace");
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
}
