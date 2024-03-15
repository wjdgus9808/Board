package com.board.repository;

import com.board.domain.Comment;
import com.board.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CommentRepository {
    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }
    public List<Comment> findByPostId(Long postId) {
        return em.createQuery("select c from Comment c where c.post.id=:postId", Comment.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    public Comment findByCommentId(Long commentId) {
        return em.find(Comment.class, commentId);
    }
    public List<Comment> findByMemberId(Long memberId) {
        return em.createQuery("select c from Comment c where c.member.id:=memberId", Comment.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public void remove(Comment comment) {
        em.remove(comment);
    }

}
