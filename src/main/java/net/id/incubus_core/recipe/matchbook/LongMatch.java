package net.id.incubus_core.recipe.matchbook;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

public class LongMatch extends Match {

    private long targetLong;

    public LongMatch(String name, String key) {
        super(name, key);
    }

    @Override
    boolean matches(ItemStack stack) {
        var nbt = stack.getNbt();

        if(nbt != null && nbt.contains(key)) {
            return nbt.getLong(key) == targetLong;
        }

        return false;
    }

    @Override
    void configure(JsonObject json) {
        targetLong = json.get("target").getAsLong();
    }

    @Override
    void configure(PacketByteBuf buf) {
        targetLong = buf.readLong();
    }

    @Override
    void write(PacketByteBuf buf) {
        buf.writeLong(targetLong);
    }

    public static class Factory extends MatchFactory<LongMatch> {

        public Factory() {
            super("long");
        }

        @Override
        public LongMatch create(String key, JsonObject object) {
            var match = new LongMatch(name, key);
            match.configure(object);

            return match;
        }

        @Override
        public LongMatch fromPacket(PacketByteBuf buf) {
            var match = new LongMatch(name, buf.readString());
            match.configure(buf);

            return match;
        }
    }

}
