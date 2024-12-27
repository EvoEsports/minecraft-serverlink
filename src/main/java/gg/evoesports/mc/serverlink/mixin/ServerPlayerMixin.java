package gg.evoesports.mc.serverlink.mixin;

import gg.evoesports.mc.serverlink.Serverlink;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin {
    @Shadow public abstract void playerTick();

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void onDeath(DamageSource damageSource, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;

        var message = damageSource.getDeathMessage(player).getString();
        var playerName = player.getDisplayName();
        
        if (playerName == null) {
            return;
        }
        
        Serverlink.syncManager.pushDeathMessage(message, playerName.getString());
    }
}
