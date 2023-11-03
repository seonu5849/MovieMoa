package org.zerock.myapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.MemberDTO;
import org.zerock.myapp.domain.MemberVO;
import org.zerock.myapp.mapper.MemberMapper;

@Log4j2
@RequiredArgsConstructor

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    public Integer saveMember(MemberDTO dto) {
        log.trace("saveMember({}) invoked.", dto);

        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        return this.memberMapper.registrationMember(dto);
    } // saveMember

    @Override
    public Boolean checkDupleEmail(String email) {
        log.trace("checkDupleEmail({}) invoked.", email);

        Integer countEmail = this.memberMapper.checkDupleEmail(email);
        log.info("\t+ countEmail: {}", countEmail);
        if (countEmail == 0) {
            // 중복된 이메일 없음
            return true;
        } else {
            // 중복된 이메일 있음
            return false;
        }
    } // checkDuple

    @Override
    public MemberVO loadUserByUserEmail(String email)  throws UsernameNotFoundException {
        log.trace("loadUserByUserEmail({}) invoked.", email);

        // DB 내 사용자 정보 확인
        MemberVO memberVO = memberMapper.loadUserByUserEmail(email);
        if (memberVO == null) {
            throw new UsernameNotFoundException("사용자 정보 찾지 못함.");
        }
        return memberVO;
    } // loadUserByUserEmail

    @Override
    public Integer lastLoginDate(String email) {
        return memberMapper.lastLoginDate(email);
    } // lastLoginDate

    @Override
    public String findUserEmail(String name, String phoneNum) {
        log.trace("findUserEmail({}, {}) invoked.", name, phoneNum);

        return memberMapper.findUserEmail(name, phoneNum);
    } // findUserEmail

    @Override
    public MemberVO findUserPassword(String email, String name, String phoneNum) {
        log.trace("findUserPassword({}, {}, {}) invoked.", email, name, phoneNum);

        MemberVO memberVO = memberMapper.findUserPassword(email, name, phoneNum);
        if (memberVO == null) {
            throw new UsernameNotFoundException("사용자 정보 찾지 못함.");
        }

        return memberVO;
    } // findUserPassword

    @Override
    public Integer updatePassword(Long id, String confirmPassword) {
        log.trace("updatePassword({}, {}) invoked.", id, confirmPassword);


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String changePW = passwordEncoder.encode(confirmPassword);

        return this.memberMapper.updateUserByPassword(id, changePW);

    } // updatePassword

    @Override
    public MemberVO findUser(Long id) {
        log.trace("findUser({}) invoked.", id);
        return memberMapper.findUser(id);
    } // findUser

    @Override
    public Integer updateMypageMember(String confirmPassword, String nickname, String address, String phoneNum, String birthday, Long id) {
        log.trace("updateMypageMember({}, {}, {}, {}, {}) invoked.", confirmPassword, nickname, address, phoneNum, birthday, id);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(confirmPassword != null){
            String changePW = passwordEncoder.encode(confirmPassword);
            return memberMapper.updateMypageMember(changePW, nickname, address, phoneNum, birthday, id);
        } else {
            return memberMapper.updateMypageMember(null, nickname, address, phoneNum, birthday, id);
        }
    } // updateMypageMember

    @Override
    public Integer deleteUser(Long id) {
        log.trace("deleteUser({}) invoked.", id);

        return memberMapper.deleteMyPageMember(id);
    }

} // end class
