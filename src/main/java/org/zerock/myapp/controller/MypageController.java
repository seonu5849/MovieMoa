package org.zerock.myapp.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.myapp.domain.MemberVO;
import org.zerock.myapp.service.MemberService;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/mypage/*")
@Controller
public class MypageController {

    // http://localhost:8080/mypage/menu/myInfo

    private final MemberService memberService;

    @GetMapping("/menu/myInfo")
    public String myInfoView(Model model) {
        log.trace("myInfoView() invoked.");

        // 현재 인증된 사용자의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증된 사용자의 이름(여기서는 사용자 ID로 사용)을 가져옵니다.
        String username = authentication.getName();
        // 사용자 이름(아이디)를 Long 타입으로 변환합니다.
        Long id = Long.valueOf(username);

        // 사용자 ID를 사용하여 회원 정보를 조회합니다.
        MemberVO member = memberService.findUser(id);

        log.info("\t+ member: {}", member);

        model.addAttribute("member", member);

        return "/mypage/myInfo";

    } // myInfoView

    @GetMapping("/authentication")
    public String mypageAuthenticationView(Model model) {
        log.trace("mypageAuthenticationView() invoked.");

        return "/mypage/authentication";
    } // mypageAuthenticationView


    @PostMapping("/authentication")
    public String mypageAuthentication(Model model, @RequestParam String passwordInput) {
        log.trace("mypageAuthentication({}, {}) invoked.", passwordInput);

        // 현재 인증된 사용자의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증된 사용자의 이름(여기서는 사용자 ID로 사용)을 가져옵니다.
        String username = authentication.getName();
        // 사용자 이름(아이디)를 Long 타입으로 변환합니다.
        Long id = Long.valueOf(username);

        // 사용자 ID를 사용하여 회원 정보를 조회합니다.
        MemberVO member = memberService.findUser(id);
        log.info("\t+ member: {}", member);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(passwordEncoder.matches(passwordInput, member.getPassword())){
            return "redirect:/mypage/changeInfo";
        } else {
            model.addAttribute("error", true);
            return "/mypage/authentication";
        }

    } // mypageAuthentication

    @GetMapping("/changeInfo")
    public String mypageChangeInfoView(Model model) {
        log.trace("mypageChangeInfoView({}) invoked.");

        // 현재 인증된 사용자의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증된 사용자의 이름(여기서는 사용자 ID로 사용)을 가져옵니다.
        String username = authentication.getName();
        // 사용자 이름(아이디)를 Long 타입으로 변환합니다.
        Long id = Long.valueOf(username);

        // 사용자 ID를 사용하여 회원 정보를 조회합니다.
        MemberVO member = memberService.findUser(id);
        log.info("\t+ member: {}", member);

        model.addAttribute("member", member);

        return "/mypage/changeInfo";
    } // mypageChangeInfoView

    @PutMapping("/changeInfo")
    public String mypageChangeInfo(Model model, String passwordInput, String confirmPassword, @RequestParam String nickname, @RequestParam String[] address, @RequestParam String phoneNum, @RequestParam String birthday) {
        log.trace("mypageChangeInfo({}, {}, {}, {}, {}) invoked.", passwordInput, confirmPassword, nickname, Arrays.toString(address), phoneNum);

        // 현재 인증된 사용자의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증된 사용자의 이름(여기서는 사용자 ID로 사용)을 가져옵니다.
        String username = authentication.getName();
        // 사용자 이름(아이디)를 Long 타입으로 변환합니다.
        Long id = Long.valueOf(username);

        // 사용자 ID를 사용하여 회원 정보를 조회합니다.
        MemberVO member = memberService.findUser(id);
        log.info("\t+ member: {}", member);
        model.addAttribute("member", member);

        // 주소를 결합합니다.
        String fullAddress = String.join(",", address[0], address[1], address[2]);
        log.info("fullAddress({})", fullAddress);
        Integer updated;
        // 주소가
        if(fullAddress.equals(",,")){
            fullAddress=member.getAddress();
        }

        if (passwordInput.isEmpty() && confirmPassword.isEmpty()) {
            // 비밀번호 입력이 둘 다 비어있는 경우
            updated = memberService.updateMypageMember(null, nickname, fullAddress, phoneNum, birthday, id);
            log.info("\t+ password null updated: {}", updated);

            model.addAttribute("updateSuccess", true);
        } else if (passwordInput.equals(confirmPassword)) {
            // 비밀번호 입력이 같은 경우
            updated = memberService.updateMypageMember(confirmPassword, nickname, fullAddress, phoneNum, birthday, id);
            log.info("\t+ updated: {}", updated);

            model.addAttribute("updateSuccess", true);
        } else {
            // 비밀번호 입력이 다른 경우
            log.info("\t+ 비밀번호 오류");
            model.addAttribute("error", true);
        }

        return "/mypage/changeInfo";

    } // mypageChangeInfo
    @DeleteMapping("/changeInfo")
    public String DeleteMypageUser(Model model, HttpSession session)
    {
        // 현재 인증된 사용자의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증된 사용자의 이름(여기서는 사용자 ID로 사용)을 가져옵니다.
        String username = authentication.getName();
        // 사용자 이름(아이디)를 Long 타입으로 변환합니다.
        Long id = Long.valueOf(username);

//        // 사용자 ID를 사용하여 회원 정보를 조회합니다.
//        MemberVO member = memberService.findUser(id);
//        log.info("\t+ member: {}", member);
//        model.addAttribute("member", member);
        Integer deleted;
        deleted = memberService.deleteUser(id);
        log.info("\t+ UserDeleted : {}", deleted);

        session.invalidate();

        return "/";

    } // DeleteMypageUser

//    @PostMapping("/myBoard")
//    public String mypageBoardView() {
//        log.trace("mypageBoardView() invoked.");
//
//        return "redirect:/board/detailBoard";
//    } // mypageBoardView
//
//    @PostMapping("/myReply")
//    public String mypageMyReply() {
//        log.trace("mypageMyReply() invoked.");
//
//        return "redirect:/board/mypageMyReply";
//    } // mypageMyReply

    @GetMapping("/menu/searchList")
    public String mypageSearchList() {
        log.trace("mypageSearchList() invoked.");

        return "/mypage/searchList";
    } // mypageSearchList

//    @PostMapping("/ask")
//    public String mypageAsk() {
//        log.trace("mypageAsk() invoked.");
//
//        return "redirect:/mypage/askDetail";
//    } // mypageAsk
//
//    @PostMapping("/ask")
//    public String mypageWriteAsk() {
//        log.trace("mypageWriteAsk() invoked.");
//
//        return "redirect:/mypage/writeAsk";
//    } // mypageWriteAsk
//
//    @PostMapping("/writeAsk")
//    public String mypageRegisterAsk() {
//        log.trace("mypageRegisterAsk() invoked.");
//
//        return "redirect:/mypage/ask";
//    } // mypageRegisterAsk
//
//    @PostMapping("/wishList")
//    public String mypageWishList() {
//        log.trace("mypageWishList() invoked.");
//
//        return "redirect:/movie/movieDetail";
//    } // mypageWishList
//
//    @DeleteMapping("/wishList")
//    public String mypageWishListDelete() {
//        log.trace("mypageWishListDelete() invoked.");
//
//        return "redirect:/mypage/wishList";
//    } // mypageWishListDelete

} // MypageController
