package org.payten.hazelcastexample.post.service;

import org.payten.hazelcastexample.post.Post;

import java.util.List;

public interface PostService {

    Post findByTitle(String title);

    List<Post> findAll();

    Post add(Post post);

    void update(Post post);

    void delete(String title);
}
