package com.michael.apache.zookeeper;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheBridge;
import org.apache.curator.framework.recipes.cache.CuratorCacheBridgeBuilder;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

/**
 * https://github.com/apache/curator/blob/master/curator-examples/src/main/java/framework/CreateClientExamples.java
 */
@Slf4j
public class CreateClientExamples {


    @Test
    public void testWatcher() throws Exception {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = createWithOptions("localhost:2181", retryPolicy, 100, 10000);
        client.start();

        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath("/michael", "Michael".getBytes());

        final CuratorCache cache = CuratorCache.bridgeBuilder(client, "/michael").build();
        cache.start();
        cache.listenable().addListener((type, oldData, data) -> {

            System.out.println(type);
            System.out.println(new String(oldData.getData()));
            System.out.println(new String(data.getData()));
        });

        new CountDownLatch(1).await();

        client.close();
    }


    /**
     * 路径监听
     * @throws Exception
     */
    @Test
    public void testChildWatcher() throws Exception {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = createWithOptions("localhost:2181", retryPolicy, 100, 10000);
        client.start();

        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath("/child", "Michael".getBytes());

        final CuratorCache cache = CuratorCache.bridgeBuilder(client, "/child").build();
        cache.start();
        cache.listenable().addListener((type, oldData, data) -> {

            System.out.println(type);
            System.out.println(new String(oldData.getData()));
            System.out.println(new String(data.getData()));
        });

        new CountDownLatch(1).await();

        client.close();
    }



    @Test
    public void testConnect() throws Exception {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = createWithOptions("localhost:2181", retryPolicy, 100, 10000);
        client.start();

        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath("/michael", "Michael".getBytes());

        byte[] bytes = client.getData().forPath("/michael");
        String s = new String(bytes);

        client.close();
    }

    public CuratorFramework createSimple(String connectionString) {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        return CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
    }

    public static CuratorFramework createWithOptions(String connectionString, RetryPolicy retryPolicy, int connectionTimeoutMs, int sessionTimeoutMs) {

        return CuratorFrameworkFactory.builder()
                .connectString(connectionString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .build();
    }

}
