package com.board.service;

import com.board.domain.Posts;
import com.board.dto.PostDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
class PostServiceTest {

    @Autowired
    PostService postService;
    @Test
    public void 전체post조회() throws Exception{
        List<PostDto.Response> list = postService.list();

        Assertions.assertThat(list.size()).isEqualTo(1);

     }

}