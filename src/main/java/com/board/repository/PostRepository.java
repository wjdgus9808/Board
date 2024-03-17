package com.board.repository;

import com.board.domain.Posts;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;
    public List<Posts> findAll() {
        return em.createQuery("select p from Posts p join fetch p.member m", Posts.class)
                .getResultList();
    }

    public List<Posts> findByTitle(String title) {
        return em.createQuery("select p from posts p where p.title like :title", Posts.class)
                .getResultList();
    }

    public Posts findById(Long id) {
        return em.find(Posts.class, id);
    }
    public void remove(Long id) {
        Posts posts = em.find(Posts.class, id);
        System.out.println("posts = " + posts);
        em.remove(posts);
    }
    public void save(Posts post) {
        em.persist(post);
    }

}
