package net.id.incubus_core.recipe.matchbook;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

public class IntMatch extends Match {

    private int targetInt;

    public IntMatch(String name, String key) {
        super(name, key);
    }

    @Override
    boolean matches(ItemStack stack) {
        var nbt = stack.getOrCreateNbt();

        if(nbt.contains(key)) {
            return nbt.getInt(key) == targetInt;
        }

        return false;
    }

    @Override
    void configure(JsonObject json) {
        targetInt = json.get("target").getAsInt();
    }

    @Override
    void configure(PacketByteBuf buf) {
        targetInt = buf.readInt();
    }

    @Override
    void write(PacketByteBuf buf) {
        buf.writeInt(targetInt);
    }

    public static class Factory extends MatchFactory<IntMatch> {

        public Factory() {
            super("int");
        }

        @Override
        public IntMatch create(String key, JsonObject object) {
            var match = new IntMatch(name, key);
            match.configure(object);

            return match;
        }

        @Override
        public IntMatch fromPacket(PacketByteBuf buf) {
            var match = new IntMatch(name, buf.readString());
            match.configure(buf);

            return match;
        }
    }

}
