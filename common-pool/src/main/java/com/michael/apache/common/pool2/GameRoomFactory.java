package com.michael.apache.common.pool2;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @auth Michael
 */
public class GameRoomFactory extends BasePooledObjectFactory<GameRoom> {


    @Override
    public GameRoom create() throws Exception {
        return new GameRoom();
    }

    @Override
    public PooledObject<GameRoom> wrap(GameRoom obj) {
        return new DefaultPooledObject<>(obj);
    }
}
