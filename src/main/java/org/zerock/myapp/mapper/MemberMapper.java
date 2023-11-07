package org.zerock.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zerock.myapp.domain.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {

    // 회원가입한 회원 등록
    public abstract Integer registrationMember(@Param("dto") MemberDTO dto);

    // 이메일 중복체크
    public abstract Integer checkDupleEmail(@Param("email") String email);

    // 아이디 확인 -> 시큐리티에서 패스워드 비교 후 로그인
    public abstract MemberVO loadUserByUserEmail(@Param("email") String email);
    
    // 최종 로그인 날짜 입력
    public abstract Integer lastLoginDate(@Param("email") String email);

    // 이메일 찾기
    public abstract String findUserEmail(@Param("name") String name, @Param("phoneNum") String phoneNum);

    // 비밀번호 찾기
    public abstract MemberVO findUserPassword (@Param("email") String email, @Param("name") String name, @Param("phoneNum") String phoneNum);

    // 비밀번호 변경
    public abstract Integer updateUserByPassword (@Param("id") Long id, @Param("password") String password);

    // 특정 회원정보 조회
    public abstract MemberVO findUser(@Param("id") Long id);

    // 특정 회원정보 변경
    public abstract Integer updateMypageMember(@Param("changePW") String changePW, @Param("nickname") String nickname, @Param("address") String address, @Param("phoneNum") String phoneNum,  @Param("birthday") String birthday, @Param("id") Long id);

    // 특정 회원 삭제(탈퇴)
    public abstract Integer deleteMyPageMember(@Param("id") Long id);

    // 특정 회원 게시글 목록 조회
    public abstract List<BoardVO> findMyBoardList(@Param("memberId") Long memberId,
                                                  @Param("offset") Integer offset,
                                                  @Param("perPage") Integer perPage);

    // 특정 회원 게시글 수
    public abstract Integer totalMyBoardCount(@Param("memberId") Long memberId);

    // 특정 회원 댓글 목록 조회
    public abstract List<BoardReplyVO> findMyReplyList(@Param("memberId") Long memberId,
                                                       @Param("offset") Integer offset,
                                                       @Param("perPage") Integer perPage);

    // 특정 회원 댓글 수
    public abstract Integer totalMyReplyCount(@Param("memberId") Long memberId);

    // 특정 회원 영화 검색기록 조회
    public abstract List<MovieVO> findSearchMovie(@Param("memberId") Long memberId);


}// end interface
