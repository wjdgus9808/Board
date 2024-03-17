package com.board.repository;

import com.board.domain.Likes;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LikeRepository {

    private final EntityManager em;

    public void save(Likes likes) {
        em.persist(likes);
    }

    public void delete(Likes likes) {
        em.remove(likes);
    }
    public List<Likes> findAll() {
        return em.createQuery("select l from Likes l", Likes.class)
                .getResultList();
    }

    public Likes findById(Long likeId) {
        return em.find(Likes.class, likeId);
    }

    public List<Likes> findByMemberId(Long memberId) {
        return em.createQuery("select l from Likes l where l.member.id=: memberId", Likes.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public Optional<Likes> findByPostIdAndMemberId(Long postId, Long memberId) {
        return em.createQuery("select l From Likes l where l.post.id=:postId " +
                        "and l.member.id=:memberId", Likes.class)
                .setParameter("postId", postId)
                .setParameter("memberId", memberId)
                .getResultList()
                .stream().findAny();
    }

}
