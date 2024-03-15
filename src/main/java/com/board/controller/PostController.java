package com.board.controller;

import com.board.common.SessionConst;
import com.board.common.argumentResolver.Login;
import com.board.common.file.FileStore;
import com.board.domain.Member;
import com.board.domain.Posts;
import com.board.dto.CommentRequestDto;
import com.board.dto.PostDto;
import com.board.dto.SessionDto;
import com.board.service.MemberService;
import com.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final MemberService memberService;
    private final FileStore fileStore;

    @GetMapping({"/","/posts"})
    public String posts(@Login SessionDto member, Model model) {
        if (member != null ) {
            Member loginMember = memberService.findById(member.getId());
            if (loginMember != null) {
                model.addAttribute("loginMember", loginMember);
            }
        }
        List<PostDto.Response> posts = postService.list();
        model.addAttribute("posts", posts);
        return "posts/posts";
    }

    @GetMapping("/posts/{postId}")
    public String post(@PathVariable(name = "postId") Long postId,@ModelAttribute(name = "commentForm") CommentRequestDto commentForm ,Model model) {
        PostDto.Response post = postService.findOne(postId);
        model.addAttribute("post", post);
        return "posts/postDetail";
    }

    /**
     * 이미지 파일 보여주기
     * 이미지파일 - > 바이너리이므로 ResponseBody
     */
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable(name = "filename") String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @DeleteMapping("/posts/{postId}")
    public String delete(@PathVariable(name = "postId") Long postId) {
        postService.postDelete(postId);
        return "redirect:/posts";
    }

    @GetMapping("/posts/new")
    public String writeForm(@ModelAttribute(name = "post") PostDto.EditForm post) {
        return "posts/editForm";
    }

    @PostMapping("/posts/new")
    public String editForm(@Login SessionDto member, @ModelAttribute PostDto.EditForm form) throws IOException {
        postService.postSave(member.getId(), form);
        return "redirect:/";
    }
    //게시글 수정
    @GetMapping("posts/{postId}/edit")
    public String editForm(@PathVariable(name = "postId") Long postId, Model model) {
        PostDto.Response post = postService.findOne(postId);
        model.addAttribute("post", post);
        return "posts/updateForm";
    }


    @PutMapping("/posts/{postId}")
    public String update(@PathVariable(name = "postId") Long postId, @ModelAttribute(name = "post") PostDto.EditForm post) throws IOException {
        postService.postUpdate(postId, post);
        return "redirect:/posts/{postId}";
    }
}
