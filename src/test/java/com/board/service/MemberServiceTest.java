package com.board.service;

import com.board.domain.Member;
import com.board.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

//    @Test
//    public void 회원가입() throws Exception{
//        //given
//        Member member = Member.builder().nickname("kim").build();
//        //when
//        Long savedId = memberService.join(member);
//        //then
//        assertThat(member).isEqualTo(memberRepository.findOne(savedId));
//
//     }
//
//     @Test
//     public void 회원정보수정() throws Exception{
//         //given
//         Member member = Member.builder()
//                 .nickname("userA")
//                 .password("123")
//                 .build();
//         memberService.join(member);
//         //when
//         Long updatedId = memberService.update(member.getId(), "modified", "modified");
//         //then
//         assertThat(memberRepository.findOne(updatedId).getNickname()).isEqualTo("modified");
//
//      }
}