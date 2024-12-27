package gg.evoesports.mc.serverlink.mixin;

import gg.evoesports.mc.serverlink.Serverlink;
import gg.evoesports.mc.serverlink.sync.model.PlayerJoinType;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerJoinLeaveHandlerMixin {
    @Inject(method = "onPlayerConnect", at = @At(value = "TAIL"))
    private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, ConnectedClientData clientData, CallbackInfo ci) {
        if (!Serverlink.config.syncJoinLeave) {
            return;
        }

        var playerName = player.getDisplayName();

        if (playerName == null) {
            return;
        }
        
        Serverlink.syncManager.pushJoinLeaveMessage(PlayerJoinType.JOIN, playerName.getString());
    }
    
    @Inject(method = "remove", at = @At(value = "TAIL"))
    private void onPlayerLeave(ServerPlayerEntity player, CallbackInfo ci) {
        if (!Serverlink.config.syncJoinLeave) {
            return;
        }

        var playerName = player.getDisplayName();

        if (playerName == null) {
            return;
        }
        
        Serverlink.syncManager.pushJoinLeaveMessage(PlayerJoinType.LEAVE, playerName.getString());
    }
}
