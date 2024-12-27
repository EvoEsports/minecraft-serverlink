package gg.evoesports.mc.serverlink.sync.model;

import com.google.gson.Gson;

public class PlayerJoinLeaveMessage extends SyncModel {
    private PlayerJoinType type;
    private String player;
    private String server;

    public PlayerJoinLeaveMessage(){}
    
    public PlayerJoinLeaveMessage(PlayerJoinType type, String player) {
        this.type = type;
        this.player = player;
    }
    
    public PlayerJoinType getType() {
        return type;
    }

    public String getPlayer() {
        return player;
    }

    public String getServer() {
        return server;
    }
    
    public void setType(PlayerJoinType type) {
        this.type = type;
    }
    
    public void setPlayer(String player) {
        this.player = player;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public static PlayerJoinLeaveMessage fromJson(String message) {
        return new Gson().fromJson(message, PlayerJoinLeaveMessage.class);
    }
}
