package net.gorbov.dao;

/**
 * Created by Mihail on 13.01.2015.
 */
public class CachedMessage  {


    private final long timeLife;
    private final Message message;

    public CachedMessage(Message message){
        this.message = message;
        timeLife = System.currentTimeMillis() + Cache.TIME_OUT;
    }

    public Message getMessage(){
        return message;
    }

    public boolean isLive(long currentTimeMillis) {
        return currentTimeMillis < timeLife;
    }


}
