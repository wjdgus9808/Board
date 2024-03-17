package com.board.repository;

import com.board.domain.Likes;
import com.board.domain.Member;
import com.board.domain.Posts;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
class LikeRepositoryTest {

    @Autowired
    LikeRepository likeRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    public void 좋아요누르기() throws Exception{
        //given
        Member member = memberRepository.findById(1L);
        Posts post = postRepository.findById(1L);

        //when
        Likes like = Likes.builder().member(member).post(post).build();
        likeRepository.save(like);
        //then

        Optional<Likes> findLike = likeRepository.findByPostIdAndMemberId(post.getId(), member.getId());
        assertThat(findLike.get()).isEqualTo(like);
    }

    @Test
    public void 좋아요_취소() throws Exception{
        //given
        Member member = memberRepository.findById(1L);
        Posts post = postRepository.findById(1L);
        Likes like = Likes.builder().member(member).post(post).build();
        likeRepository.save(like);
        //when
        likeRepository.delete(like);
        //then
        assertThat(likeRepository.findById(like.getId())).isNull();
     }


}