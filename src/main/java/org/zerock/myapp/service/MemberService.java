package org.zerock.myapp.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zerock.myapp.domain.MemberDTO;
import org.zerock.myapp.domain.MemberVO;

public interface MemberService {

    // 회원가입한 회원 등록
    public abstract Integer saveMember(MemberDTO dto);

    // 이메일 중복체크
    public abstract Boolean checkDupleEmail(String email);

    // 아이디 확인 -> 시큐리티에서 패스워드 비교 후 로그인
    public abstract MemberVO loadUserByUserEmail(String email);

    // 최종 로그인 날짜 입력
    public abstract Integer lastLoginDate(String email);

    // 이메일 찾기
    public abstract String findUserEmail (String name, String phoneNum);

    // 비밀번호 찾기
    public abstract MemberVO findUserPassword (String email, String name, String phoneNum);

    // 비밀번호 변경
    public abstract Integer updatePassword(Long id, String confirmPassword);

    // 특정 회원정보 조회
    public abstract MemberVO findUser(Long id);

    // 특정 회원정보 변경
    public abstract Integer updateMypageMember(String confirmPassword, String nickname, String address, String phoneNum,  String birthday, Long id);

    // 특정 회원 삭제(탈퇴)
    public abstract Integer deleteUser(@Param("id") Long id);

} // end interface
