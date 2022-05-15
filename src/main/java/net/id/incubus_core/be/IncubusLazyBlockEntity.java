package net.id.incubus_core.be;

import net.id.incubus_core.IncubusCore;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public abstract class IncubusLazyBlockEntity extends IncubusBaseBE {

    protected boolean initialized;
    private final int tickSpacing, tickOffset;

    public IncubusLazyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int tickSpacing) {
        super(type, pos, state);
        this.tickSpacing = tickSpacing;
        this.tickOffset = tickSpacing > 1 ? IncubusCore.RANDOM.nextInt(tickSpacing) : 0;
    }

    public IncubusLazyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        this(type, pos, state, 0);
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, T be) {
        var entity = (IncubusLazyBlockEntity) be;
        if(!entity.initialized) {
            entity.initialized = entity.initialize(world, pos, state);
        }
        if(entity.hasInitialized()) {
            entity.tick(pos, state);
            if(!world.isClient()) {
                entity.tickServer(pos, state);
            }
        }
    }

    protected abstract void tick(BlockPos pos, BlockState state);

    public void tickServer(BlockPos pos, BlockState state) {}

    public boolean allowTick() {
        return tickSpacing == 0 || (world.getTime() + tickOffset) % tickSpacing == 0;
    }

    protected boolean initialize(World world, BlockPos pos, BlockState state) {
        return true;
    }

    public boolean hasInitialized() {
        return initialized;
    }

    public void save(NbtCompound nbt) {
        super.save(nbt);
        nbt.putBoolean("initialized", initialized);
    }

    public void load(NbtCompound nbt) {
        super.load(nbt);
        initialized = nbt.getBoolean("initialized");
    }
}
