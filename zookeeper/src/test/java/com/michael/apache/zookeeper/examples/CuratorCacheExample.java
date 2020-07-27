package com.michael.apache.zookeeper.examples;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @auth Michael
 * https://github.com/apache/curator/blob/master/curator-examples/src/main/java/cache/CuratorCacheExample.java
 *
 */
public class CuratorCacheExample {

    private static final String PATH = "/example/cache";

    public static void main(String[] args) throws Exception
    {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        try
        {
            try (CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", new ExponentialBackoffRetry(1000, 3)))
            {
                client.start();
                try (CuratorCache cache = CuratorCache.build(client, PATH))
                {
                    // there are several ways to set a listener on a CuratorCache. You can watch for individual events
                    // or for all events. Here, we'll use the builder to log individual cache actions
                    CuratorCacheListener listener = CuratorCacheListener.builder()
                            .forCreates(node -> {
                                System.out.println(String.format("Node created: [%s]", node));})
                            .forChanges((oldNode, node) -> System.out.println(String.format("Node changed. Old: [%s] New: [%s]", oldNode, node)))
                            .forDeletes(oldNode -> System.out.println(String.format("Node deleted. Old value: [%s]", oldNode)))
                            .forInitialized(() -> System.out.println("Cache initialized"))
                            .build();

                    // register the listener
                    cache.listenable().addListener(listener);

                    // the cache must be started
                    cache.start();

                    // now randomly create/change/delete nodes
                    for ( int i = 0; i < 1000; ++i )
                    {
                        int depth = random.nextInt(1, 4);
                        String path = makeRandomPath(random, depth);
                        if ( random.nextBoolean() )
                        {
                            client.create().orSetData().creatingParentsIfNeeded().forPath(path, Long.toString(random.nextLong()).getBytes());
                        }
                        else
                        {
                            client.delete().quietly().deletingChildrenIfNeeded().forPath(path);
                        }

                        Thread.sleep(5);
                    }
                }
            }
        } finally {

        }
    }

    private static String makeRandomPath(ThreadLocalRandom random, int depth)
    {
        if ( depth == 0 )
        {
            return PATH;
        }
        return makeRandomPath(random, depth - 1) + "/" + random.nextInt(3);
    }
}
