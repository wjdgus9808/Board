package com.board.dto;

import com.board.domain.Comment;
import com.board.domain.Member;
import com.board.domain.Posts;
import com.board.domain.UploadFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class EditForm {
        String title;
        String text;
        List<MultipartFile> imageFiles;
    }

    @Getter @Setter
    @AllArgsConstructor
    public static class Response {
        private Member member;
        private Long id;
        private String title;
        private String text;
        private List<UploadFile> imageFiles;
        private LocalDateTime createdDate;
        private List<Comment> comments;
        private Long view;

        public Response(Posts posts) {
            id = posts.getId();
            member = posts.getMember();
            imageFiles = posts.getUploadFiles();
            comments = posts.getComments();
            title = posts.getTitle();
            text = posts.getText();
            createdDate = posts.getCreatedDate();
            view = posts.getView();
        }
    }



}
