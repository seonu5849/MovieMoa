package org.zerock.myapp.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.zerock.myapp.domain.MemberVO;

import java.util.Collection;

@Log4j2

public class SecurityUser extends User { // 스프링 시큐리티의 User 클래스를 상속
    private static  final double serialVersionUID = 1L; // 직렬화에 사용되는 고유 버전 ID

    // 사용자 이름, 비밀번호 및 권한 목록을 매개변수로 받는 생성자
    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    // MemberVO 객체를 매개변수로 받는 생성자
    public SecurityUser(MemberVO member){
        super(
                String.valueOf(member.getId()), // 사용자 ID를 문자열로 변환하여 사용자 이름으로 설정
//                "{noop}"+member.getPassword(), // 비밀번호 앞에 {noop}를 추가하여 평문 비밀번호로 처리
                "{bcrypt}"+member.getPassword(), // 비밀번호 앞에 {bcrypt}를 추가하여 bcrypt 암호화 방식으로 처리
                AuthorityUtils.createAuthorityList(member.getRole().name()) // 사용자의 역할을 기반으로 권한 목록 생성
        );

        log.trace("SecurityUser({}) invoked", member); // 로그 기록
    } // constructor

} // end class
