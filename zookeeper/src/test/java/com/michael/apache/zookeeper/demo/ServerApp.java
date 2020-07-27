package com.michael.apache.zookeeper.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;


import java.util.UUID;

/**
 * @author Michael
 */
public class ServerApp {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
        client.start();
        ServiceRegistry serviceRegistrar = new ServiceRegistry(client,"services");
        ServiceInstance<InstanceDetails> instance1 = ServiceInstance.<InstanceDetails>builder()
                .name("service1")
                .port(12345)
                .address("localhost")   //address不写的话，会取本地ip
                .payload(new InstanceDetails(UUID.randomUUID().toString(),"localhost",12345,"Test.Service1"))
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();
        ServiceInstance<InstanceDetails> instance2 = ServiceInstance.<InstanceDetails>builder()
                .name("service2")
                .port(12345)
                .address("localhost")
                .payload(new InstanceDetails(UUID.randomUUID().toString(),"localhost",12345,"Test.Service2"))
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();
        serviceRegistrar.registerService(instance1);
        serviceRegistrar.registerService(instance2);


        Thread.sleep(Integer.MAX_VALUE);
    }
}
