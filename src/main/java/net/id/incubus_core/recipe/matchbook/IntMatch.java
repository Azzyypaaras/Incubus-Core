package net.id.incubus_core.recipe.matchbook;

import com.google.gson.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;

public class IntMatch extends Match {
    public static final String TYPE = "int";

    private int targetInt;

    public IntMatch(String name, String key) {
        super(name, key);
    }

    @Override
    boolean matches(NbtCompound nbt) {
        if(nbt != null && nbt.contains(key)) {
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
    JsonObject toJson() {
        JsonObject main = new JsonObject();
        main.add("type", new JsonPrimitive(TYPE));
        main.add("target", new JsonPrimitive(targetInt));
        return main;
    }

    @Override
    void write(PacketByteBuf buf) {
        buf.writeInt(targetInt);
    }

    public static class Factory extends MatchFactory<IntMatch> {

        public Factory() {
            super(TYPE);
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
