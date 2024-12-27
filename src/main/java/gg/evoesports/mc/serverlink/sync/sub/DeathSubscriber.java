package gg.evoesports.mc.serverlink.sync.sub;

import gg.evoesports.mc.serverlink.Serverlink;
import gg.evoesports.mc.serverlink.sync.model.PlayerDeathMessage;
import gg.evoesports.mc.serverlink.util.ChatMessageFormatter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import redis.clients.jedis.JedisPubSub;

import java.util.Objects;

public class DeathSubscriber extends JedisPubSub {
    public static final String Channel = "serverlink.death";
    private MinecraftServer server;

    public DeathSubscriber(MinecraftServer server) {
        this.server = server;
    }

    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equals(Channel)) {
            return;
        }

        PlayerDeathMessage deathMessage = PlayerDeathMessage.fromJson(message);

        if (deathMessage == null || Objects.equals(deathMessage.getServerName(), Serverlink.config.serverName)) {
            return;
        }

        server.getPlayerManager().broadcast(Text.literal(ChatMessageFormatter.deathMessage(deathMessage)), false);
    }
}
