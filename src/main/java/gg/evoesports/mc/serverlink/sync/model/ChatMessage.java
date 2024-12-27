package gg.evoesports.mc.serverlink.sync.model;

import com.google.gson.Gson;

public class ChatMessage extends SyncModel {
    private String playerName;
    private String message;

    public ChatMessage() {}
    
    public ChatMessage(String playerName, String message) {
        this.playerName = playerName;
        this.message = message;
    }
    
    public static ChatMessage fromJson(String message) {
        return new Gson().fromJson(message, ChatMessage.class);
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getMessage() {
        return message;
    }
    
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
