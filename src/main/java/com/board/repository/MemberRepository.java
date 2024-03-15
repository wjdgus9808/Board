package com.board.repository;

import com.board.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }

    public Optional<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.username = :name",Member.class)
                .setParameter("name", name)
                .getResultList().stream().findAny();
    }

    public void update(Long id, String nickname, String password) {
        Member member = em.find(Member.class, id);
        member.modify(nickname, password);
    }


}
