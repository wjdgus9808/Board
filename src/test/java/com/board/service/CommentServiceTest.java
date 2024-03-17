package com.board.service;

import com.board.domain.Comment;
import com.board.domain.Member;
import com.board.domain.Posts;
import com.board.dto.CommentRequestDto;
import com.board.repository.CommentRepository;
import com.board.repository.MemberRepository;
import com.board.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class CommentServiceTest {

    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;
    @Test
    public void 댓글추가테스트() throws Exception{
        //given
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setText("첫번째 댓글입니다");
        Member member = memberRepository.findById(1L);
        //when
        Long commentId = commentService.addComment(1L, commentRequestDto, member.getId());
        //then
        Comment findComment = commentRepository.findByCommentId(commentId);
        assertThat(findComment.getParent()).isNull();
        assertThat(findComment.getMember()).isEqualTo(member);
    }
    @Test
    public void 대댓글테스트() throws Exception{
        //given
        Member memberA = memberRepository.findById(1L);
        Member memberB = memberRepository.findById(2L);
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setText("첫번째 댓글입니다");
        Long firstId = commentService.addComment(1L, commentRequestDto, memberA.getId());

        //when
        CommentRequestDto commentRequestDto2 = new CommentRequestDto();
        commentRequestDto2.setText("두번째 댓글입니다");
        commentRequestDto2.setParentId(firstId);
        Long secondId = commentService.addComment(1L, commentRequestDto2, memberB.getId());

        CommentRequestDto commentRequestDto3 = new CommentRequestDto();
        commentRequestDto3.setText("세번째 댓글입니다");
        commentRequestDto3.setParentId(firstId);
        Long thirdId = commentService.addComment(1L, commentRequestDto3, memberB.getId());
        //then
        //첫 댓글
        Comment first = commentRepository.findByCommentId(firstId);
        //두번째 댓글
        Comment second = commentRepository.findByCommentId(secondId);
        List<Comment> list = commentRepository.findByPostId(1L);
        Posts post = postRepository.findById(1L);
        System.out.println("post.getComments() = " + post.getComments());
        System.out.println("list = " + list);
        assertThat(list).contains(first).contains(second);
        assertThat(second.getParent()).isEqualTo(first);
    }
    
}