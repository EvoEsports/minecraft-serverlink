package gg.evoesports.mc.serverlink.sync;

import gg.evoesports.mc.serverlink.Serverlink;
import gg.evoesports.mc.serverlink.config.ModConfig;
import gg.evoesports.mc.serverlink.sync.model.ChatMessage;
import gg.evoesports.mc.serverlink.sync.model.PlayerDeathMessage;
import gg.evoesports.mc.serverlink.sync.model.PlayerJoinLeaveMessage;
import gg.evoesports.mc.serverlink.sync.model.PlayerJoinType;
import gg.evoesports.mc.serverlink.sync.sub.ChatSubscriber;
import gg.evoesports.mc.serverlink.sync.sub.DeathSubscriber;
import gg.evoesports.mc.serverlink.sync.sub.JoinLeaveSubscriber;
import net.minecraft.server.MinecraftServer;

public class SyncManager {
    private RedisManager redisManager;
    private ModConfig config;
    
    public SyncManager(ModConfig config) {
        this.redisManager = new RedisManager(config.redisHost, config.redisPort);
        this.config = config;
    }
    
    public void initialize(MinecraftServer server) {
        if (config.syncChat) redisManager.subscribe(new ChatSubscriber(server), ChatSubscriber.Channel);
        if (config.syncJoinLeave) redisManager.subscribe(new JoinLeaveSubscriber(server), JoinLeaveSubscriber.Channel);
        if (config.syncDeathMessages) redisManager.subscribe(new DeathSubscriber(server), DeathSubscriber.Channel);

        Serverlink.LOGGER.info("SyncManager initialized");
    }
    
    public void pushChatMessage(String message, String playerName) {
        if (!config.syncChat) {
            return;
        }

        ChatMessage chatMessage = new ChatMessage(playerName, message);
        chatMessage.setServerName(this.config.serverName);
        
        redisManager.publish(ChatSubscriber.Channel, chatMessage);
    }
    
    public void pushJoinLeaveMessage(PlayerJoinType type, String playerName) {
        if (!config.syncJoinLeave) {
            return;
        }

        PlayerJoinLeaveMessage message = new PlayerJoinLeaveMessage(type, playerName);
        message.setServerName(this.config.serverName);

        redisManager.publish(JoinLeaveSubscriber.Channel, message);
    }
    
    public void pushDeathMessage(String message, String playerName) {
        if (!config.syncDeathMessages) {
            return;
        }
        
        PlayerDeathMessage deathMessage = new PlayerDeathMessage(message, playerName);
        deathMessage.setServerName(this.config.serverName);

        redisManager.publish(DeathSubscriber.Channel, deathMessage);
    }
}
