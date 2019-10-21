package com.michael.common.pool;

import lombok.Getter;
import lombok.Setter;

public class Jedis {

    @Getter
    @Setter
    boolean validate = false;

    public Jedis() {
        System.out.println("init");
    }

    public void destroy() {
        System.out.println("-------------- destroy ------------");
    }


    public void doSomething() {
        System.out.println("doSomething");
    }

}
