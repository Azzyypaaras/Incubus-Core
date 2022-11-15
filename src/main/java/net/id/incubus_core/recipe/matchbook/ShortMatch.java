package net.id.incubus_core.recipe.matchbook;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

public class ShortMatch extends Match {

    private short targetShort;

    public ShortMatch(String name, String key) {
        super(name, key);
    }

    @Override
    boolean matches(ItemStack stack) {
        var nbt = stack.getOrCreateNbt();

        if(nbt.contains(key)) {
            return nbt.getShort(key) == targetShort;
        }

        return false;
    }

    @Override
    void configure(JsonObject json) {
        targetShort = json.get("target").getAsShort();
    }

    @Override
    void configure(PacketByteBuf buf) {
        targetShort = buf.readShort();
    }

    @Override
    void write(PacketByteBuf buf) {
        buf.writeShort(targetShort);
    }

    public static class Factory extends MatchFactory<ShortMatch> {

        public Factory() {
            super("short");
        }

        @Override
        public ShortMatch create(String key, JsonObject object) {
            var match = new ShortMatch(name, key);
            match.configure(object);

            return match;
        }

        @Override
        public ShortMatch fromPacket(PacketByteBuf buf) {
            var match = new ShortMatch(name, buf.readString());
            match.configure(buf);

            return match;
        }
    }

}
