import net.gorbov.Cache;
import net.gorbov.Message;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Mihail on 20.01.2015.
 */
public class TestCache {

    private static long id = 1;

    private Message getTestMessage(){

        Message message = new Message();
        message.setId(id++);
        message.setAuthor("" + message.getId());
        message.setDateCreated(new Date());
        message.setMessage(""+message.getId());

        return message;
    }

    @Test
    public void testCash(){

        long lifeTime = 2 * 1000;
        Cache cache = new Cache(1,lifeTime);
        Message message = getTestMessage();
        cache.setMessage(message);
        Message message2 = cache.getMessage(message.getId());

        assertSame(message,message);

        try {
            Thread.sleep(2100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int count = cache.getCount();
        assertTrue(count == 0);

        message2 = cache.getMessage(message.getId());
        assertNull(message2);

    }
}
