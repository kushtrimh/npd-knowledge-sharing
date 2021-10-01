package org.payten.hazelcastexample.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.config.ReplicatedMapConfig;
import com.hazelcast.config.ScheduledExecutorConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import com.hazelcast.spring.context.SpringManagedContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Configuration
@Component
@EnableCaching
public class HazelcastConfig {

    @Bean
    public SpringManagedContext springManagedContext() {
        return new SpringManagedContext();
    }

    @Bean
    public HazelcastInstance exampleHazelcastInstance(SpringManagedContext springManagedContext,
                                                      HazelcastProperties hazelcastProperties) {
        Config config = new Config("hazelcast-example");
        config.setClusterName("hazelcast-example-cluster");
        config.setManagedContext(springManagedContext);
        ManagementCenterConfig managementCenterConfig = new ManagementCenterConfig();
        managementCenterConfig.setTrustedInterfaces(Collections.singleton("127.0.0.1"));
        config.setManagementCenterConfig(managementCenterConfig);
        NetworkConfig networkConfig = config.getNetworkConfig();
        networkConfig.setPort(6700)
                .setPortAutoIncrement(true)
                .setPortCount(10);
        JoinConfig joinConfig = networkConfig.getJoin();
        joinConfig.getMulticastConfig()
                .setEnabled(false);
        joinConfig.getTcpIpConfig()
                .setEnabled(true)
                .setMembers(hazelcastProperties.getMembers());
        config.addMapConfig(new MapConfig("posts")
                .setAsyncBackupCount(1));
        config.addReplicatedMapConfig(new ReplicatedMapConfig("some-other-replicated-map"));
        config.addQueueConfig(new QueueConfig("some-other-queue"));
        config.addScheduledExecutorConfig(new ScheduledExecutorConfig("posts-scheduler")
                .setDurability(1));
        return Hazelcast.getOrCreateHazelcastInstance(config);
    }

    @Bean
    public CacheManager cacheManager(@Qualifier("exampleHazelcastInstance") HazelcastInstance hazelcastInstance) {
        return new HazelcastCacheManager(hazelcastInstance);
    }
}
