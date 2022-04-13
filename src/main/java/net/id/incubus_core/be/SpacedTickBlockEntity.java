package net.id.incubus_core.be;

import com.google.common.base.Preconditions;
import net.id.incubus_core.IncubusCore;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public abstract class SpacedTickBlockEntity extends BlockEntity {
    protected boolean initialized;
    private final int tickSpacing, tickOffset;
    private boolean shouldClientRemesh = true;

    public SpacedTickBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int tickSpacing) {
        super(type, pos, state);
        this.tickSpacing = tickSpacing;
        this.tickOffset = tickSpacing > 1 ? IncubusCore.RANDOM.nextInt(tickSpacing) : 0;
    }

    public SpacedTickBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        this(type, pos, state, 0);
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, T be) {
        var entity = (SpacedTickBlockEntity) be;
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

    @Override
    public final NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = super.toInitialChunkDataNbt();
        saveClient(nbt);
        nbt.putBoolean("#c", shouldClientRemesh); // mark client tag
        shouldClientRemesh = false;
        return nbt;
    }

    @Override
    public final BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void sync() {
        sync(true);
    }

    public void sync(boolean shouldRemesh) {
        Preconditions.checkNotNull(world); // Maintain distinct failure case from below
        if (!(world instanceof ServerWorld))
            throw new IllegalStateException("Cannot call sync() on the logical client! Did you check world.isClient first?");

        shouldClientRemesh = shouldRemesh | shouldClientRemesh;
        ((ServerWorld) world).getChunkManager().markForUpdate(getPos());
    }

    @Override
    protected final void writeNbt(NbtCompound nbt) {
        save(nbt);
    }

    @Override
    public final void readNbt(NbtCompound nbt) {
        if (nbt.contains("#c")) {
            loadClient(nbt);
            if (nbt.getBoolean("#c")) {
                remesh();
            }
        } else {
            load(nbt);
        }
    }

    public void save(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("initialized", initialized);
    }

    public void load(NbtCompound nbt) {
        super.readNbt(nbt);
        initialized = nbt.getBoolean("initialized");
    }

    public abstract void saveClient(NbtCompound nbt);

    public abstract void loadClient(NbtCompound nbt);

    public final void remesh() {
        Preconditions.checkNotNull(world);
        if (!(world instanceof ClientWorld))
            throw new IllegalStateException("Cannot call remesh() on the server!");

        world.updateListeners(pos, null, null, 0);
    }
}
