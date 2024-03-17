package com.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Posts {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    @Lob
    private String text;
    private Long view; //조회수

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id asc")
    private List<Comment> comments = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "upload_file",joinColumns =@JoinColumn(name="post_id"))
    private List<UploadFile> uploadFiles = new ArrayList<>();

    private Integer likeCount = 0;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    public void modify(String title,String text,List<UploadFile> uploadFiles) {
        this.title = title;
        this.text = text;
        this.uploadFiles = uploadFiles;
        modifiedDate = LocalDateTime.now();
    }

    //연관관계 편의메서드
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void increaseLike() {
        this.likeCount += 1;
    }
    public void decreaseLike() {
        this.likeCount -= 1;
    }

}
