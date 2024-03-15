package com.board.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username",unique = true)
    private String username;
    private String password;
    private String nickname;
    private String email;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    public void modify(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        this.modifiedDate = LocalDateTime.now();
    }

}
