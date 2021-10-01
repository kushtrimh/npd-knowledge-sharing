package org.payten.hazelcastexample.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.concurrent.Callable;

public class PostTask implements Callable<Boolean>, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(PostTask.class);

    private final Post post;
    private final int personIdentifier;

    public PostTask(Post post, int personIdentifier) {
        this.post = post;
        this.personIdentifier = personIdentifier;
    }

    @Override
    public Boolean call() throws Exception {
        logger.info("Sending information about {} to person {}", post, personIdentifier);
        return true;
    }
}
