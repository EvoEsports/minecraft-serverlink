package gg.evoesports.mc.serverlink.sync.model;

import com.google.gson.Gson;

public class PlayerDeathMessage extends SyncModel {
    private String message;
    private String playerName;
    
    public PlayerDeathMessage() {}
    
    public PlayerDeathMessage(String message, String playerName) {
        this.message = message;
        this.playerName = playerName;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public String getPlayerName() {

        return playerName;
    }

    public void setPlayerName(String playerName) {

        this.playerName = playerName;
    }
    
    public static PlayerDeathMessage fromJson(String json) {
        return new Gson().fromJson(json, PlayerDeathMessage.class);
    }
}
