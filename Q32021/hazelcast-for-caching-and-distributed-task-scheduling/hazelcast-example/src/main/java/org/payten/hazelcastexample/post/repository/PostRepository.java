package org.payten.hazelcastexample.post.repository;

import org.payten.hazelcastexample.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    Post findByTitle(String title);
}
