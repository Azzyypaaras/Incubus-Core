package net.id.incubus_core.recipe.matchbook;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

public class StringMatch extends Match {

    private String targetString;

    public StringMatch(String name, String key) {
        super(name, key);
    }

    @Override
    boolean matches(ItemStack stack) {
        var nbt = stack.getOrCreateNbt();

        if(nbt.contains(key)) {
            return nbt.getString(key).equals(targetString);
        }

        return false;
    }

    @Override
    void configure(JsonObject json) {
        targetString = json.get("target").getAsString();
    }

    @Override
    void configure(PacketByteBuf buf) {
        targetString = buf.readString();
    }

    @Override
    void write(PacketByteBuf buf) {
        buf.writeString(targetString);
    }

    public static class Factory extends MatchFactory<StringMatch> {

        public Factory() {
            super("string");
        }

        @Override
        public StringMatch create(String key, JsonObject object) {
            var match = new StringMatch(name, key);
            match.configure(object);

            return match;
        }

        @Override
        public StringMatch fromPacket(PacketByteBuf buf) {
            var match = new StringMatch(name, buf.readString());
            match.configure(buf);

            return match;
        }
    }

}
