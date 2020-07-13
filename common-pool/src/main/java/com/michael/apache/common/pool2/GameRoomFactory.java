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

    @Override
    public void activateObject(PooledObject<GameRoom> p) throws Exception {
        super.activateObject(p);
        System.out.println("activateObject");
    }

    @Override
    public void passivateObject(PooledObject<GameRoom> p) throws Exception {
        super.passivateObject(p);
        System.out.println("passivateObject");
    }

    @Override
    public boolean validateObject(PooledObject<GameRoom> p) {
        System.out.println("validateObject");
        return super.validateObject(p);
    }
}
