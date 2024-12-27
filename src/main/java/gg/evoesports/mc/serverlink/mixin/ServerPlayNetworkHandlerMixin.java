package gg.evoesports.mc.serverlink.mixin;

import gg.evoesports.mc.serverlink.Serverlink;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "onChatMessage", at = @At("HEAD"))
    private void onChatMessage(ChatMessageC2SPacket packet, CallbackInfo ci) {
        if (!Serverlink.config.syncChat) {
            return;
        }

        var playerName = player.getDisplayName();

        if (playerName == null) {
            return;
        }
        
        Serverlink.syncManager.pushChatMessage(packet.chatMessage(), playerName.getString());
    }
}
