package org.zerock.myapp.service;

import org.zerock.myapp.domain.MemberDTO;
import org.zerock.myapp.domain.MemberVO;

import java.util.List;

public interface AdminService {

    // 전체 회원 조회: 페이징 처리를 포함해 전체 회원 목록을 조회
    public abstract List<MemberVO> findAllMember(Integer pageNum);

    // 특정 이름 또는 이메일로 회원 검색: 이름 또는 이메일로 회원을 검색. 페이징 처리 포함.
    public abstract List<MemberVO> searchMemberNameOrEmail(Integer pageNum, String searchOption, String searchValue);

    // 회원 상세 정보 조회: 특정 회원의 상세 정보를 조회
    public abstract MemberVO findDetailMember(Long id);

    // 회원 추가: 새로운 회원을 데이터베이스에 추가
    public abstract Integer joinMember(MemberDTO dto);

    // 회원 정보 업데이트: 기존 회원의 정보를 업데이트
    public abstract Integer editMember(Long id, String nickname, String address, String[] phoneNum, String role);

    // 회원 삭제: 특정 회원을 데이터베이스에서 삭제
    public abstract Integer deleteMember(Long[] selectedMembers);

    // 총 회원 수 조회: 데이터베이스에 있는 전체 회원 수를 조회
    public abstract Integer totalPage();

    // 검색 조건에 따른 회원 수 조회: 이름, 이메일, 또는 닉네임으로 검색된 회원의 수를 조회
    public abstract Integer totalSearchPage(String searchOption, String searchValue);

    public abstract Integer offset(Integer pageNum);

} // end interface
