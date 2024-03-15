package com.board.service;

import com.board.domain.Comment;
import com.board.domain.Member;
import com.board.domain.Posts;
import com.board.dto.CommentRequestDto;
import com.board.repository.CommentRepository;
import com.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberService memberService;
    @Transactional
    public Long addComment(Long postId, CommentRequestDto commentRequestDto, Long memberId) {
        Posts post = postRepository.findById(postId);
        Member member = memberService.findById(memberId);
        Comment comment = commentRequestDto.toEntity(member);
        //대댓글이면
        if (commentRequestDto.getParentId() != null) {
            Comment parent = commentRepository.findByCommentId(commentRequestDto.getParentId());
            comment.setParent(parent);
            parent.getChild().add(comment);
        }

        post.addComment(comment);
        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public void removeComment(Long postId, Long commentId) {
        if(postRepository.findById(postId)!=null){
            Comment removeComment = commentRepository.findByCommentId(commentId);
            commentRepository.remove(removeComment);
        }

    }
}
