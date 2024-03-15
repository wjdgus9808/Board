package com.board.controller;

import com.board.common.SessionConst;
import com.board.domain.Member;
import com.board.dto.MemberDto;
import com.board.dto.SessionDto;
import com.board.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberDto.MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid @ModelAttribute MemberDto.MemberForm memberForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("error= {}", bindingResult);
            return "members/createMemberForm";
        }
        try {
            memberService.join(memberForm);
        } catch (IllegalStateException e) {
            bindingResult.reject("existedMember","존재하는 회원입니다");
            log.error("error= {}", bindingResult);
            return "members/createMemberForm";
        }
        return "redirect:/";
    }

    /*
        로그인
     */
    @GetMapping("/members/login")
    public String loginForm(@ModelAttribute("member") MemberDto.LoginForm member) {
        return "members/loginForm";
    }

    @PostMapping("/members/login")
    public String login(@Valid @ModelAttribute("member") MemberDto.LoginForm member, BindingResult bindingResult, Model model,
                        @RequestParam(defaultValue = "/",name = "redirectURL") String redirectURL,
                        HttpServletRequest request) {
        log.info("redirectURL = {}", redirectURL);
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "members/loginForm";
        }
        Member loginMember = memberService.login(member);
        if (loginMember == null) {
            bindingResult.reject("loginFail", "에러");
            log.info("errors = {}", bindingResult);
            return "members/loginForm";

        }
        //성공로직 TODO
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, new SessionDto(loginMember.getId(), loginMember.getUsername(), loginMember.getNickname()));
        return "redirect:"+redirectURL;
    }

    @PostMapping("/members/logout")
    public String logOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
