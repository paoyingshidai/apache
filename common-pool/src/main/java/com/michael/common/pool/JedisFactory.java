package com.michael.common.pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class JedisFactory  implements PooledObjectFactory<Jedis> {

    @Override
    public PooledObject<Jedis> makeObject() throws Exception {
        return new DefaultPooledObject<>(new Jedis());
    }

    @Override
    public void destroyObject(PooledObject<Jedis> p) throws Exception {

        System.out.println("destroyObject");
        Jedis object = p.getObject();
        object.destroy();
    }

    /**
     * 校验这个对象是否有效
     * @param p
     * @return
     */
    @Override
    public boolean validateObject(PooledObject<Jedis> p) {
        System.out.println("validateObject");
        Jedis object = p.getObject();

        return object.isValidate();
    }

    @Override
    public void activateObject(PooledObject<Jedis> p) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<Jedis> p) throws Exception {

    }
}
