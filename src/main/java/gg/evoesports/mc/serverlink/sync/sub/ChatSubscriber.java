package gg.evoesports.mc.serverlink.sync.sub;

import gg.evoesports.mc.serverlink.Serverlink;
import gg.evoesports.mc.serverlink.sync.model.ChatMessage;
import gg.evoesports.mc.serverlink.util.ChatMessageFormatter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import redis.clients.jedis.JedisPubSub;

import java.util.Objects;

public class ChatSubscriber extends JedisPubSub {
    public static final String Channel = "serverlink.chat";
    private MinecraftServer server;
    
    public ChatSubscriber(MinecraftServer server) {
        this.server = server;
    }
    
    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equals(Channel)) {
            return;
        }

        ChatMessage chatMessage = ChatMessage.fromJson(message);
        
        if (chatMessage == null || Objects.equals(chatMessage.getServerName(), Serverlink.config.serverName)) {
            return;
        }

        String formattedMessage = ChatMessageFormatter.chatMessage(chatMessage);
        server.getPlayerManager().broadcast(Text.literal(formattedMessage), false);
    }
}
