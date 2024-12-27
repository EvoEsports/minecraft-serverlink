package gg.evoesports.mc.serverlink.config;

public class ModConfig {
    public final String redisHost;
    public final int redisPort;
    public final boolean syncChat;
    public final boolean syncDeathMessages;
    public final boolean syncJoinLeave;
    public final String serverName;

    public ModConfig() {
        this.redisHost = ModConfigParser.getString("REDIS_HOST");
        this.redisPort = ModConfigParser.getInt("REDIS_PORT");

        this.syncChat = ModConfigParser.getBoolean("SYNC_CHAT");
        this.syncDeathMessages = ModConfigParser.getBoolean("SYNC_DEATH_MESSAGES");
        this.syncJoinLeave = ModConfigParser.getBoolean("SYNC_JOIN_LEAVE");
        
        this.serverName = ModConfigParser.getString("SERVER_NAME");
    }
}
