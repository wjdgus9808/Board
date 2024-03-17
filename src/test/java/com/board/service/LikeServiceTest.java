package com.board.service;

import com.board.domain.Likes;
import com.board.domain.Member;
import com.board.domain.Posts;
import com.board.repository.LikeRepository;
import com.board.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class LikeServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    LikeService likeService;
    @Autowired
    LikeRepository likeRepository;

    @Test
    public void 좋아요누르기() throws Exception{
        //given
        Member member = memberService.findById(1L);
        Posts post = postRepository.findById(1L);
        //when
        likeService.doLike(member.getId(),post.getId());
        //then

        Likes likes = likeRepository.findByPostIdAndMemberId(member.getId(), post.getId()).orElseThrow();
        assertThat(likes.getMember()).isEqualTo(member);
    }

    @Test
    @DisplayName("좋아요는 한번만 누를 수 있다.")
    public void 좋아요_두번() throws Exception{
        //given
        Member member = memberService.findById(1L);
        Posts post = postRepository.findById(1L);
        //when
        likeService.doLike(member.getId(),post.getId());

        //then
        assertThrows(IllegalStateException.class,()->likeService.doLike(member.getId(),post.getId()));


    }

    @Test
    @DisplayName("좋아요 취소")
    public void 좋아요_취소() throws Exception{
        //given
        Member member = memberService.findById(1L);
        Posts post = postRepository.findById(1L);
        likeService.doLike(post.getId(),member.getId());
        //when
        likeService.cancelLike(post.getId(),member.getId());
        //then
        Optional<Likes> findLike = likeRepository.findByPostIdAndMemberId(post.getId(), member.getId());
        assertThat(findLike).isEmpty();

    }

    @Test
    @DisplayName("좋아요를 누르지 않은상태에서 취소할수없습니다.")
    public void 좋아요_취소2() throws Exception{
        //given
        Member member = memberService.findById(1L);
        Posts post = postRepository.findById(1L);
        //when

        //then
        assertThrows(IllegalStateException.class, () -> {
            likeService.cancelLike(post.getId(), member.getId());
        });

    }
}