package com.board.service;

import com.board.domain.Likes;
import com.board.domain.Member;
import com.board.domain.Posts;
import com.board.repository.LikeRepository;
import com.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {
    private final LikeRepository likeRepository;
    private final MemberService memberService;
    private final PostRepository postRepository;
    @Transactional
    public void doLike(Long postId, Long memberId) {
        Optional<Likes> existedLike = likeRepository.findByPostIdAndMemberId(postId, memberId);
        if (existedLike.isPresent()) {
            throw new IllegalStateException("좋아요는 한번만 가능합니다");
        }
        Posts post = postRepository.findById(postId);
        Member member = memberService.findById(memberId);
        Likes like = Likes.builder()
                .post(post)
                .member(member)
                .createdDate(LocalDateTime.now())
                .build();
        post.increaseLike();
        likeRepository.save(like);
    }

    @Transactional
    public void cancelLike(Long postId, Long memberId) throws IllegalStateException {
        Optional<Likes> existedLike = likeRepository.findByPostIdAndMemberId(postId, memberId);
        Posts post = postRepository.findById(postId);
        existedLike.ifPresentOrElse(like-> {
            likeRepository.delete(like);
            post.decreaseLike();
        },()->{throw new IllegalStateException("좋아요가 없습니다.");});
    }

}
