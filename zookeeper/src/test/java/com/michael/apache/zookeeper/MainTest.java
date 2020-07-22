package com.michael.apache.zookeeper;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class MainTest {

    @Test
    public void test() {

        Set<Integer> set = new HashSet<Integer>();
        set.add(new Integer(999));

        System.out.println(set.contains(new Integer(999)));

        System.out.println(Integer.valueOf(1) == (Integer.valueOf(1)));
    }

}