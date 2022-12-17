package net.id.incubus_core.recipe.matchbook;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

public class ByteMatch extends Match {

    private byte targetByte;

    public ByteMatch(String name, String key) {
        super(name, key);
    }

    @Override
    boolean matches(ItemStack stack) {
        var nbt = stack.getOrCreateNbt();

        if(nbt.contains(key)) {
            return nbt.getByte(key) == targetByte;
        }

        return false;
    }

    @Override
    void configure(JsonObject json) {
        targetByte = json.get("target").getAsByte();
    }

    @Override
    void configure(PacketByteBuf buf) {
        targetByte = buf.readByte();
    }

    @Override
    void write(PacketByteBuf buf) {
        buf.writeByte(targetByte);
    }

    public static class Factory extends MatchFactory<ByteMatch> {

        public Factory() {
            super("byte");
        }

        @Override
        public ByteMatch create(String key, JsonObject object) {
            var match = new ByteMatch(name, key);
            match.configure(object);

            return match;
        }

        @Override
        public ByteMatch fromPacket(PacketByteBuf buf) {
            var match = new ByteMatch(name, buf.readString());
            match.configure(buf);

            return match;
        }
    }

}
