package com.board.controller;

import com.board.common.argumentResolver.Login;
import com.board.dto.CommentRequestDto;
import com.board.dto.SessionDto;
import com.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}")
    public String addComment(@PathVariable(name = "postId") Long postId,
                             @ModelAttribute(name = "comment") CommentRequestDto commentRequestDto,
                             @Login SessionDto sessionDto) {
        Long commentId = commentService.addComment(postId, commentRequestDto, sessionDto.getId());
        return "redirect:/posts/{postId}";
    }

    @DeleteMapping("/{postId}/{commentId}")
    public String deleteComment(@PathVariable(name = "postId") Long postId,
                                @PathVariable(name = "commentId") Long commentId) {
        commentService.removeComment(postId, commentId);
        return "redirect:/posts/{postId}";
    }
}
