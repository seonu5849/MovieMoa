package org.zerock.myapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.myapp.domain.MemberDTO;
import org.zerock.myapp.domain.MemberVO;
import org.zerock.myapp.service.MemberService;
import org.zerock.myapp.service.MemberServiceImpl;
import org.zerock.myapp.service.MemberUserDetailsService;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor

@Controller
public class MainController {

    private final MemberService memberService;
    private final MemberUserDetailsService userService;

    @GetMapping("/")
    public String index(Model model) {
        log.trace("index() invoked.");

        // SecurityContextHolder를 통해 현재 인증된 사용자의 Authentication 객체를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증된 사용자가 있을 경우
        if (authentication != null) {
            // 현재 인증된 사용자의 이름을 가져옴
            String username = authentication.getName();
            // 현재 인증된 사용자가 가진 권한 목록을 가져옴
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            // 인증 시 사용된 자격 증명 정보를 가져옴
            Object credentials = authentication.getCredentials();
            // 인증된 사용자의 주요 정보를 나타내는 객체를 가져옴
            Object principal = authentication.getPrincipal();
            // 인증과 관련된 추가 세부 정보를 가져옴
            Object details = authentication.getDetails();

            // 사용자의 권한 목록을 순회
            for (GrantedAuthority authority : authorities) {
                String authorityName = authority.getAuthority();
                // 여기서 권한별 처리를 할 수 있음
            }

            // 뷰로 전달할 정보를 모델에 추가
            model.addAttribute("username", username);
            model.addAttribute("authorities", authorities);
            model.addAttribute("credentials", credentials);
            model.addAttribute("principal", principal);
            model.addAttribute("details", details);
        }

        return "/main";
    } // index 메서드 종료


    @GetMapping("/login")
    public String loginView(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String errorMessage = (String) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            if (errorMessage != null) {
                model.addAttribute("errorMessage", errorMessage);
            }
        }
        return "/login";
    } // login

    @PostMapping("/login")
    public String login(@RequestParam String email) {
        log.trace("login() invoked.");

        return "redirect:/";
    } // login

    @GetMapping("/registration")
    public String registrationView() {
        log.trace("registrationView() invoked.");

        return "/registration";
    } // registrationView

    @PostMapping("/registration")
    public String registration(MemberDTO dto){
        log.trace("registration() invoked.");

        this.memberService.saveMember(dto);

        return "redirect:/login";
    } // registration

    @ResponseBody
    @PostMapping("/checkEmail")
    public Map<String, Boolean> checkEmail(@RequestBody Map<String, String> request){
        log.trace("checkEmail({}) invoked.", request);
        String email = request.get("email");

        // 이메일 중복 체크시 true면 회원가입 페이지의 비동기 체크를 위해
        // isEmailAvailable로 return
        if(!email.equals("")){
            Boolean dupleEmail = this.memberService.checkDupleEmail(email);

            return Collections.singletonMap("isEmailAvailable", dupleEmail);
        } else {
            return Collections.singletonMap("isEmailAvailable", false);
        }

    } // checkEmail

    @GetMapping("/IdSearch")
    public String IdSearchView() {
        log.trace("IdSearchView() invoked.");

        return "IdSearch";
    } //IdSearchView

    @PostMapping("/IdSearch")
    public String findEmail(@RequestParam String name, @RequestParam String phoneNum, Model model){
        log.trace("findEmail({}, {}) invoked.", name, phoneNum);

        // 사용자 이름과 전화번호를 이용하여 사용자의 이메일 주소를 찾음
        String userEmail = memberService.findUserEmail(name, phoneNum);

        // 찾은 이메일 주소가 null이라면, 올바른 정보가 아니라는 메시지를 모델에 추가
        if (userEmail == null) {
            model.addAttribute("userEmail", "올바른 정보가 아닙니다.");
        } else {
            // 이메일 주소를 찾았다면, 찾은 이메일 주소를 모델에 추가
            model.addAttribute("userEmail", userEmail);
        }

        return "FindResult";

    } // findEmail

    @GetMapping("/PasswordSearch")
    public String PasswordSearchView() {
        log.trace("PasswordSearchView() invoked.");

        return "PasswordSearch";
    } //PasswordSearch

    @PostMapping("/PasswordSearch")
    public String PasswordSearch(@RequestParam String email, @RequestParam String name, @RequestParam String phoneNum, Model model){
        log.trace("PasswordSearchView({}, {}, {}) invoked.", email, name, phoneNum);

        MemberVO member = memberService.findUserPassword(email, name, phoneNum);

        if(member != null) {
            log.info("\t+ member: {}", member);
            model.addAttribute("member", member);
            return "/UpdatePassword";
        } else {
            model.addAttribute("error", true);
            return "/PasswordSearch";
        }

    } //PasswordSearch

    @PutMapping("/UpdatePassword")
    public String ChangePassword(Model model, @RequestParam Long id,
                                 @RequestParam String password,
                                 @RequestParam String confirmPassword){
        log.trace("ChangePassword({}, {}, {}) invoked.", id, password, confirmPassword);

        MemberVO member = memberService.findUser(id);
        model.addAttribute("member", member);

        if(!password.equals("") && !confirmPassword.equals("") && password.equals(confirmPassword)){
            Integer updated = this.memberService.updatePassword(id, confirmPassword);
            log.info("\t+ updated: {}", updated);
            model.addAttribute("updateSuccess", true);
            // updatePassword true 전달 시 UpdatePassword.html
            // script 내 작성된 showConfirmationAndRedirect() 이벤트가 수행됨
            return "/UpdatePassword";
        } else {
            model.addAttribute("error", true);
            return "/UpdatePassword";
        }
    } //PasswordSearch

} // end class
