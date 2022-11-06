package net.id.incubus_core.mixin.player;

import net.id.incubus_core.misc.Players;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.MessageType;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.lang.annotation.Target;
import java.util.UUID;

import static net.id.incubus_core.misc.Players.AZZY;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Unique
    private final MutableText MARKER = (MutableText) Text.of("");

    @ModifyArgs(method = "onPlayerConnect", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V"))
    public void markJoinMessage(Args args, ClientConnection connection, ServerPlayerEntity player) {
        if (player.getUuid().equals(AZZY))
            args.set(0, MARKER);
    }

    @Inject(method = "broadcast(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V", at = @At("HEAD"), cancellable = true)
    public void cancelJoinMessage(Text message, MessageType type, UUID sender, CallbackInfo ci) {
        if (message == MARKER) {
            ci.cancel();
        }
    }
}
