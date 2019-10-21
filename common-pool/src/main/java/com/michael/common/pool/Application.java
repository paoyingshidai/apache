package com.michael.common.pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.concurrent.TimeUnit;

public class Application {

    public static void main(String[] args) {


        Pool<Jedis> pool = new Pool(new GenericObjectPoolConfig(), new JedisFactory());

        for (int i = 0; i < 10; i++) {
            // 获取资源
            Jedis resource = pool.getResource();
            resource.doSomething();


            // 返还资源
            pool.returnResource(resource);

            System.out.println(i);

        }

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
        }


        for (int i = 0; i < 10; i++) {
            Jedis resource = pool.getResource();
            resource.doSomething();

            System.out.println(i);
        }

    }

}
