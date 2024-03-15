package com.board.dto;

import com.board.domain.Comment;
import com.board.domain.Member;
import com.board.domain.Posts;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class CommentRequestDto {
    private String text;

    private Long parentId;

    public Comment toEntity(Member member) {
        return Comment.builder()
                .member(member)
                .text(text)
                .child(new ArrayList<>())
                .createdDate(LocalDateTime.now())
                .build();
    }
}
