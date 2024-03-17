package com.board.controller;

import com.board.common.argumentResolver.Login;
import com.board.dto.SessionDto;
import com.board.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}/{memberId}")
    public String like(@PathVariable Long postId, @PathVariable Long memberId) {
        likeService.doLike(postId, memberId);
        return "redirect:/posts/{postId}";
    }
}
