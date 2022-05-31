package net.id.incubus_core.render.test;

import com.google.common.base.Preconditions;
import net.id.incubus_core.IncubusCore;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class RenderTestBlockEntity extends BlockEntity {

    private boolean shouldClientRemesh = true;

    public RenderTestBlockEntity(BlockPos pos, BlockState state) {
        super(IncubusCore.RENDER_TEST_BLOCK_ENTITY_TYPE, pos, state);
    }

    @Override
    public final NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = super.toInitialChunkDataNbt();
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
            if (nbt.getBoolean("#c")) {
                remesh();
            }
        } else {
            load(nbt);
        }
    }

    public void save(NbtCompound nbt) {
        super.writeNbt(nbt);
    }

    public void load(NbtCompound nbt) {
        super.readNbt(nbt);
    }

    public final void remesh() {
        Preconditions.checkNotNull(world);
        if (!(world instanceof ClientWorld))
            throw new IllegalStateException("Cannot call remesh() on the server!");

        world.updateListeners(pos, null, null, 0);
    }
}
