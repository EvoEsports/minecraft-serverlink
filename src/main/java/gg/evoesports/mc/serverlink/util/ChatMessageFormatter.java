package gg.evoesports.mc.serverlink.util;

import gg.evoesports.mc.serverlink.sync.model.ChatMessage;
import gg.evoesports.mc.serverlink.sync.model.PlayerDeathMessage;
import gg.evoesports.mc.serverlink.sync.model.PlayerJoinLeaveMessage;

public class ChatMessageFormatter {
    public static String chatMessage(ChatMessage message) {
        return "[§7§o" + message.getServerName() + "§r] <" + message.getPlayerName() + "> " + message.getMessage();
    }
    
    public static String joinMessage(PlayerJoinLeaveMessage message) {
        return "[§7§o" + message.getServerName() + "§r] " + "§e" + message.getPlayer() + " joined the game";
    }

    public static String leaveMessage(PlayerJoinLeaveMessage message) {
        return "[§7§o" + message.getServerName() + "§r] " + "§e" + message.getPlayer() + " left the game";
    }
    
    public static String deathMessage(PlayerDeathMessage message) {
        return "[§7§o" + message.getServerName() + "§r] " + message.getMessage();
    }
}
