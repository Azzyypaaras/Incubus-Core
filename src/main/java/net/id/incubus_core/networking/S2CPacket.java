package net.id.incubus_core.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public abstract class S2CPacket implements Packet, ClientPlayNetworking.PlayChannelHandler {

    private List<ServerPlayerEntity> recipients;

    public S2CPacket() {}

    public S2CPacket(List<ServerPlayerEntity> recipients) {
        this.recipients = recipients;
    }

    @Override
    public void send() {
        var id = getId();
        var packetByteBuf = PacketByteBufs.create();
        this.write(packetByteBuf);

        for(var player : recipients)
            ServerPlayNetworking.send(player, id, packetByteBuf);
    }

    @Override
    public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        this.read(buf);
        client.execute(() -> execute(client, handler, buf, responseSender));
    }

    abstract void execute(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender);

    public static <T extends S2CPacket> void register(Identifier id, T obj) { ClientPlayNetworking.registerGlobalReceiver(id, obj); }
}
