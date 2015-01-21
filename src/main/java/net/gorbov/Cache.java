package net.gorbov;

import java.util.concurrent.*;

/**
 * Class for caching text massage. Contains class for saving  Life time object in cache
 */
public class Cache {

    private static long lifeTime;

    private ConcurrentHashMap<Long,CachedMessage> storage = new ConcurrentHashMap<Long, CachedMessage>();

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {

        public Thread newThread(Runnable r) {
            Thread th = new Thread(r);
            th.setDaemon(true);
            return th;
        }
    });

    public Cache(long cleaningPeriod, long lifeTime){

        Cache.lifeTime = lifeTime;

        scheduler.scheduleAtFixedRate(new Runnable() {

            public void run() {
                long currentTimeMillis = System.currentTimeMillis();
                for (CachedMessage cachedMessage : storage.values()) {

                    if (!cachedMessage.isLive(currentTimeMillis)){
                        storage.remove(cachedMessage.getMessage().getId());
                    }
                }
            }
        }, 1, cleaningPeriod, TimeUnit.MILLISECONDS);
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

    public int getCount(){
        return storage.size();
    }

    private class CachedMessage{
        private final long timeLife;
        private final Message message;

        public CachedMessage(Message message){
            this.message = message;
            timeLife = System.currentTimeMillis() + Cache.lifeTime;
        }

        public Message getMessage(){
            return message;
        }

        public boolean isLive(long currentTimeMillis) { //потестить методы
            return currentTimeMillis < timeLife;
        }
    }

}
