package com.michael.apache.common.pool2;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.jupiter.api.Test;

import java.util.StringJoiner;

class MainTest {

    @Test
    void testString() {

        StringJoiner joiner = new StringJoiner(" ", "ok ", "!");
        joiner.add("hello");
        joiner.add("world");
        System.out.println(joiner.toString());

    }

    @Test
    void test() throws Exception {
        GameRoomFactory gameRoomFactory = new GameRoomFactory();

        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(11);
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