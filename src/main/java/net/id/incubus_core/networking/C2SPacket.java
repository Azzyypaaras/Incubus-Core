package net.id.incubus_core.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public abstract class C2SPacket implements Packet, ServerPlayNetworking.PlayChannelHandler {

    @Environment(EnvType.CLIENT)
    public void send() {
        var id = this.getId();
        var packetByteBuf = PacketByteBufs.create();
        this.write(packetByteBuf);
        ClientPlayNetworking.send(id, packetByteBuf);
    }

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        this.read(buf);
        server.execute(() -> execute(server, player, handler, buf, responseSender));
    }

    abstract void execute(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender);

    public static <T extends C2SPacket> void register(Identifier id, T obj) { ServerPlayNetworking.registerGlobalReceiver(id, obj); }
}
