package com.michael.apache.common.pool2;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @auth Michael
 */
public class Main {

    public static void main(String[] args) throws Exception {
        GameRoomFactory gameRoomFactory = new GameRoomFactory();
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(20);
        config.setMaxTotal(100);
        config.setMinIdle(5);
        config.setTestOnCreate(true);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        GenericObjectPool<GameRoom> pool = new GenericObjectPool(gameRoomFactory, config);
        GameRoom gameRoom = null;
       try {
           gameRoom = pool.borrowObject();
           System.out.println(gameRoom);
       } finally {
           pool.returnObject(gameRoom);
       }
    }

}
