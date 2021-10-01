package org.payten.hazelcastexample.post.service;

import org.payten.hazelcastexample.post.Post;
import org.payten.hazelcastexample.post.repository.PostRepository;
import org.payten.hazelcastexample.scheduler.TaskScheduler;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultPostService implements PostService {

    private final PostRepository postRepository;
    private final TaskScheduler taskScheduler;

    public DefaultPostService(PostRepository postRepository,
                              TaskScheduler taskScheduler) {
        this.postRepository = postRepository;
        this.taskScheduler = taskScheduler;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "posts", key = "#title")
    public Post findByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "posts", key = "'allPosts'")
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "posts", key = "'allPosts'")
    }, put = {
            @CachePut(cacheNames = "posts", key = "#post.title")
    })
    public Post add(Post post) {
        Post addedPost = postRepository.save(post);
        taskScheduler.schedule(addedPost);
        return addedPost;
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "posts", key = "'allPosts'"),
            @CacheEvict(cacheNames = "posts", key = "#post.title")
    })
    public void update(Post post) {
        Post savedPost = findByTitle(post.getTitle());
        savedPost.setContent(post.getContent());
        postRepository.save(savedPost);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "posts", key = "'allPosts'"),
            @CacheEvict(cacheNames = "posts", key = "#title")
    })
    public void delete(String title) {
        Post post = findByTitle(title);
        if (post != null) {
            postRepository.delete(post);
        }
    }
}
