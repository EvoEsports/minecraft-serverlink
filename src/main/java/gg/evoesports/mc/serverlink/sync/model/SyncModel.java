package gg.evoesports.mc.serverlink.sync.model;

public abstract class SyncModel {
    private String serverName;
    
    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
