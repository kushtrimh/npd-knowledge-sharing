package org.payten.hazelcastexample.scheduler;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.scheduledexecutor.IScheduledExecutorService;
import org.payten.hazelcastexample.post.Post;
import org.payten.hazelcastexample.post.PostTask;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TaskScheduler {

    private final HazelcastInstance hazelcastInstance;

    public TaskScheduler(@Qualifier("exampleHazelcastInstance") HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    public void schedule(Post post) {
        IScheduledExecutorService scheduler = hazelcastInstance.getScheduledExecutorService("posts-scheduler");
        for (int i = 0; i < 10; i++) {
            scheduler.schedule(new PostTask(post, i), 10, TimeUnit.SECONDS);
        }
    }
}
