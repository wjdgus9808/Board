package com.board;

import com.board.domain.Comment;
import com.board.domain.Member;
import com.board.domain.Posts;
import com.board.dto.CommentRequestDto;
import com.board.dto.MemberDto;
import com.board.dto.PostDto;
import com.board.repository.CommentRepository;
import com.board.repository.MemberRepository;
import com.board.repository.PostRepository;
import com.board.service.CommentService;
import com.board.service.MemberService;
import com.board.service.PostService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    @PostConstruct
    public void init() throws IOException {
        Member member = Member.builder()
                .username("1111")
                .nickname("정현")
                .password("9972486wg@")
                .email("aa@a.com")
                .build();
        Member member2 = Member.builder()
                .username("2222")
                .nickname("보빈")
                .password("9972486wg@")
                .email("aa@a.com")
                .build();
        Long memberId = memberService.join(new MemberDto.MemberForm(member.getUsername(), member.getNickname(), member.getPassword(), member.getEmail()));
        Long memberId2 = memberService.join(new MemberDto.MemberForm(member2.getUsername(), member2.getNickname(), member2.getPassword(), member2.getEmail()));
        Posts post = Posts.builder()
                .member(member)
                .text("테스트글입니다")
                .title("테스트")
                .createdDate(LocalDateTime.now())
                .build();
        Posts post2 = Posts.builder()
                .member(member2)
                .text("보비글입니다")
                .title("보비 글")
                .createdDate(LocalDateTime.now())
                .build();
        postService.postSave(memberId,new PostDto.EditForm(post.getTitle(),post.getText(),new ArrayList<>()));
        postService.postSave(memberId2, new PostDto.EditForm(post2.getTitle(), post2.getText(), new ArrayList<>()));
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setText("첫번째 댓글입니다");
        Long firstId = commentService.addComment(1L, commentRequestDto, 1L);

        //when
        CommentRequestDto commentRequestDto2 = new CommentRequestDto();
        commentRequestDto2.setText("두번째 댓글입니다");
        commentRequestDto2.setParentId(firstId);
        Long secondId = commentService.addComment(1L, commentRequestDto2,2L);

        CommentRequestDto commentRequestDto3 = new CommentRequestDto();
        commentRequestDto3.setText("세번째 댓글입니다");
        commentRequestDto3.setParentId(firstId);
        Long thirdId = commentService.addComment(1L, commentRequestDto3, 2L);
        List<Comment> list = commentRepository.findByPostId(1L);

    }

}
