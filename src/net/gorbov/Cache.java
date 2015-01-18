package net.gorbov;

import java.util.concurrent.*;

/**
 * Class for caching text massage
 */
public class Cache {

    public final static long TIME_OUT = 60*60*1000;
    public final static long CLEANING_PERIOD = TIME_OUT/6;

    private ConcurrentHashMap<Long,CachedMessage> storage = new ConcurrentHashMap<Long, CachedMessage>();

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {

        public Thread newThread(Runnable r) {
            Thread th = new Thread(r);
            th.setDaemon(true);
            return th;
        }
    });

    public Cache(){
        scheduler.scheduleAtFixedRate(new Runnable() {

            public void run() {

                long currentTimeMillis = System.currentTimeMillis();
                for (CachedMessage cachedMessage : storage.values()) {

                    if (!cachedMessage.isLive(currentTimeMillis)){
                        storage.remove(cachedMessage.getMessage().getId());
                    }
                }
            }
        }, 1, CLEANING_PERIOD, TimeUnit.MILLISECONDS);
    }

    public Message getMessage(long id){

        CachedMessage cachedMessage = storage.get(id);
        if(cachedMessage != null) {

            boolean isLive = cachedMessage.isLive(System.currentTimeMillis());
            if (isLive) {
                return cachedMessage.getMessage();
            }
        }

        return null;
    }

    public void setMessage(Message message){

        CachedMessage cachedMessage = new CachedMessage(message);
        storage.put(message.getId(), cachedMessage);
    }

}
