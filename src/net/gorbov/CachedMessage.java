package net.gorbov;

/**
 * Class contains message and additionally message lifetime
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
