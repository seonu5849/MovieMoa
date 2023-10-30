package org.zerock.myapp.service;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.myapp.config.SecurityUser;
import org.zerock.myapp.domain.MemberVO;
import org.zerock.myapp.mapper.MemberMapper;

import java.util.Objects;

@Log4j2
@NoArgsConstructor

@Service
public class MemberUserDetailsService implements UserDetailsService {

    @Setter(onMethod_= @Autowired)
    private MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.trace("loadUserByUsername({}) invoked", email);

        Objects.requireNonNull(this.memberMapper);  // memberMapper가 null이 아닌지 확인
        log.info("\t+ this.memberMapper: {}", this.memberMapper);  // memberMapper 정보 로그 출력

        MemberVO foundMember = this.memberMapper.loadUserByUserEmail(email);  // 사용자 이름으로 사용자 정보를 조회
        log.info("\t+ foundMember : {}", foundMember);  // 찾은 사용자 정보 로그 출력

        Integer lastLoginDate = this.memberMapper.lastLoginDate(email);
        log.info("\t+ lastLoginDate: {}", lastLoginDate);

        return new SecurityUser(foundMember);  // 찾은 사용자 정보를 기반으로 SecurityUser 객체 생성 및 반환
    } // loadUserByUsername

} // end class
