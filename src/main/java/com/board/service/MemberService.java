package com.board.service;

import com.board.domain.Member;
import com.board.dto.MemberDto;
import com.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(MemberDto.MemberForm memberForm) {
        validateDuplicateMember(memberForm);
        //DTO -> Entity
        Member member = Member.builder()
                .email(memberForm.getEmail())
                .nickname(memberForm.getNickname())
                .username(memberForm.getUsername())
                .password(memberForm.getPassword())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(MemberDto.MemberForm memberForm) {
        Optional<Member> findMember = memberRepository.findByName(memberForm.getUsername());
        findMember.ifPresent(member -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member login(MemberDto.LoginForm request) {
        return memberRepository.findByName(request.getUsername())
                .filter(m -> m.getPassword().equals(request.getPassword()))
                .orElse(null);
    }
    @Transactional
    public Long update(Long id, String nickname,String password) {
        Member member = memberRepository.findById(id);
        member.modify(nickname,password);
        return member.getId();
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
