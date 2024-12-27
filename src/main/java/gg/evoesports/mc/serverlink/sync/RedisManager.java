package gg.evoesports.mc.serverlink.sync;

import com.google.gson.Gson;
import gg.evoesports.mc.serverlink.Serverlink;
import gg.evoesports.mc.serverlink.sync.model.SyncModel;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisManager {
    private JedisPool jedisPublisher;
    private JedisPool jedisSubscriber;
    ArrayList<ExecutorService> subExecutors = new ArrayList<>();
    
    public RedisManager(String host, int port) {
        this.jedisPublisher = new JedisPool(host, port);
        this.jedisPublisher.setMaxTotal(10);
        
        this.jedisSubscriber = new JedisPool(host, port);
    }
    
    public void publish(String channel, String message) {
        Jedis jedis = getPublisher();
        
        try {
            if (jedis == null) {
                logNotConnected();
                return;
            }

            jedis.publish(channel, message);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    public <TMessage extends SyncModel> void publish(String channel, TMessage message) {
        Jedis jedis = getPublisher();
        
        try {
            if (jedis == null) {
                logNotConnected();
                return;
            }

            String serialized = new Gson().toJson(message);
            Serverlink.LOGGER.info("Sending message: {}", serialized);
            jedis.publish(channel, serialized);
        } finally {
            if (jedis != null) jedis.close();
        }
    }
    
    public void subscribe(JedisPubSub jedisPubSub, String channel) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        
        executorService.submit(() -> {
            try {
                this.getSubscriber().subscribe(jedisPubSub, channel);
                Serverlink.LOGGER.debug("Subscribed to channel: {}", channel);
            } catch (Exception e) {
                Serverlink.LOGGER.error(e.getMessage(), e);
            }
        });
        
        subExecutors.add(executorService);
    }
    
    private Jedis getPublisher() {
        return getJedis(jedisPublisher);
    }
    
    private Jedis getSubscriber() {
        return getJedis(jedisSubscriber);
    }
    
    private Jedis getJedis(JedisPool pool) {
        Serverlink.LOGGER.warn("Getting Jedis from pool");
        try {
            Jedis jedis = pool.getResource();

            if (!jedis.isConnected()) {
                Serverlink.LOGGER.warn("Jedis not connected");
                return null;
            }

            Serverlink.LOGGER.warn("got jedis");
            return jedis;
        } catch (Exception e) {
            Serverlink.LOGGER.error(e.getMessage(), e);
        }

        return null;
    }
    
    private void logNotConnected() {
        Serverlink.LOGGER.warn("Not connected to redis");
    }
}
