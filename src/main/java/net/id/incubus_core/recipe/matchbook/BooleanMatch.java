package net.id.incubus_core.recipe.matchbook;

import com.google.gson.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;

public class BooleanMatch extends Match {

    private boolean booleanValue;

    public BooleanMatch(String name, String key) {
        super(name, key);
    }

    @Override
    boolean matches(NbtCompound nbt) {
        if(nbt != null && nbt.contains(key)) {
            return nbt.getBoolean(key) == booleanValue;
        }

        return false;
    }

    @Override
    void configure(JsonObject json) {
        booleanValue = json.get("value").getAsBoolean();
    }

    @Override
    void configure(PacketByteBuf buf) {
        booleanValue = buf.readBoolean();
    }

    @Override
    void write(PacketByteBuf buf) {
        buf.writeBoolean(booleanValue);
    }

    public static class Factory extends MatchFactory<BooleanMatch> {

        public Factory() {
            super("boolean");
        }

        @Override
        public BooleanMatch create(String key, JsonObject object) {
            var match = new BooleanMatch(name, key);
            match.configure(object);

            return match;
        }

        @Override
        public BooleanMatch fromPacket(PacketByteBuf buf) {
            var match = new BooleanMatch(name, buf.readString());
            match.configure(buf);

            return match;
        }
    }

}
