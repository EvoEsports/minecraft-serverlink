package gg.evoesports.mc.serverlink;

import gg.evoesports.mc.serverlink.config.ModConfig;
import gg.evoesports.mc.serverlink.sync.SyncManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Serverlink implements ModInitializer {

    public static final String MOD_ID = "serverlink";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final ModConfig config = new ModConfig();
    public static final SyncManager syncManager = new SyncManager(config);

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(Serverlink::onServerStarted);
    }
    
    private static void onServerStarted(MinecraftServer server) {
        syncManager.initialize(server);

        LOGGER.info("ServerLink initialized");
    }
}
