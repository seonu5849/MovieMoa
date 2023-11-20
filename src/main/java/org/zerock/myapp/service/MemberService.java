package org.zerock.myapp.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.zerock.myapp.domain.*;

import java.util.List;

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

    public abstract Integer offset(Integer pageNum);

    // 회원 ID를 이용한 특정 회원의 게시글 전체 조회
    public abstract List<BoardVO> findMyPageBoardList(Long memberId, Integer pageNum);

    // 페이징을 위한 특정 회원의 게시글 총 레코드 수 조회
    public abstract Integer totalMyBoardByBoardCount(Long memberId);

    // 회원 ID를 이용한 특정 회원의 리플 전체 조회
    public abstract List<BoardReplyVO> findMyPageReplyList(Long memberId, Integer pageNum);

    // 페이징을 위한 특정 회원의 리플 총 레코드 수 조회
    public abstract Integer totalMyReplyCount(Long memberId);

    // 특정 회원 영화 검색기록 조회
    public abstract List<MovieVO> findSearchMovie(Long memberId);
    
    // 특정 회원 영화 검색기록 선택 삭제
    public abstract Integer deleteHistory(Long memberId, List<Long> movieIds);

    // 특정 회원 위시리스트 조회
    public abstract List<MovieVO> findWishList(Long memberId);

    // 특정 회원 영화 위시리스트 삭제
    public abstract Integer deleteWishList(Long memberId, List<Long> movieIds);

    // 특정 회원 문의내용 조회
    public abstract List<InquiriesVO> findInquiriesList(Long memberId, Integer pageNum);

    // 페이징을 위한 특정 회원의 문의내역 총 레코드 수 조회
    public abstract Integer totalMyInquiriesCount(Long memberId);

    // 특정 회원 상세 문의내용 조회
    public abstract InquiriesVO findInquiry(@Param("id") Long id);


} // end interface
