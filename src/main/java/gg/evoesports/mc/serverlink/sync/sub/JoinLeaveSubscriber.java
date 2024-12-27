package gg.evoesports.mc.serverlink.sync.sub;

import gg.evoesports.mc.serverlink.Serverlink;
import gg.evoesports.mc.serverlink.sync.model.PlayerJoinLeaveMessage;
import gg.evoesports.mc.serverlink.sync.model.PlayerJoinType;
import gg.evoesports.mc.serverlink.util.ChatMessageFormatter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import redis.clients.jedis.JedisPubSub;

import java.util.Objects;

public class JoinLeaveSubscriber extends JedisPubSub {
    public static final String Channel = "serverlink.joinleave";
    private MinecraftServer server;

    public JoinLeaveSubscriber(MinecraftServer server) {
        this.server = server;
    }

    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equals(Channel)) {
            return;
        }

        PlayerJoinLeaveMessage joinLeaveMessage = PlayerJoinLeaveMessage.fromJson(message);

        if (joinLeaveMessage == null || Objects.equals(joinLeaveMessage.getServerName(), Serverlink.config.serverName)) {
            return;
        }

        if (joinLeaveMessage.getType() == PlayerJoinType.JOIN) {
            server.getPlayerManager().broadcast(Text.literal(ChatMessageFormatter.joinMessage(joinLeaveMessage)), false);
        } else if (joinLeaveMessage.getType() == PlayerJoinType.LEAVE) {
            server.getPlayerManager().broadcast(Text.literal(ChatMessageFormatter.leaveMessage(joinLeaveMessage)), false);
        }
    }
}
