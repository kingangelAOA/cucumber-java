package junyan.cucumber.support.util;

import redis.clients.jedis.Jedis;

/**
 * Created by kingangeltot on 15/11/10.
 */
public class RedisClient {

    private Jedis jedis;

    public void setup() {
        //连接redis服务器，192.168.0.100:6379
        jedis = new Jedis("192.168.112.207", 6379);
        //权限认证
        jedis.auth("eleMe");
    }
}
