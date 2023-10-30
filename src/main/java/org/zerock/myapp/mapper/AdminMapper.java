package org.zerock.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.myapp.domain.MemberDTO;
import org.zerock.myapp.domain.MemberVO;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMapper {

    // 전체 회원 조회: 페이징 처리를 포함해 전체 회원 목록을 조회
    public abstract List<MemberVO> selectAllMember(@Param("offset") Integer offset, @Param("perPage") Integer perPage);

    // 특정 이름 또는 이메일로 회원 검색: 이름 또는 이메일로 회원을 검색. 페이징 처리 포함.
    public abstract List<MemberVO> selectMemberNameOrEmail(@Param("params") Map<String, String> params,
                                                           @Param("offset") Integer offset,
                                                           @Param("perPage") Integer perPage);

    // 회원 상세 정보 조회: 특정 회원의 상세 정보를 조회
    public abstract MemberVO selectDetailMember(@Param("dto") MemberDTO dto);

    // 회원 추가: 새로운 회원을 데이터베이스에 추가
    public abstract Integer insertMember(@Param("dto") MemberDTO dto);

    // 회원 정보 업데이트: 기존 회원의 정보를 업데이트
    public abstract Integer updateMember(@Param("dto") MemberDTO dto);

    // 회원 삭제: 특정 회원을 데이터베이스에서 삭제
    public abstract Integer deleteMember(@Param("dto") MemberDTO dto);

    // 총 회원 수 조회: 데이터베이스에 있는 전체 회원 수를 조회
    public abstract Integer totalMemberCount();

    // 검색 조건에 따른 회원 수 조회: 이름, 이메일, 또는 닉네임으로 검색된 회원의 수를 조회
    public abstract Integer selectTotalSearchCount(@Param("params") Map<String, String> params);

} // end interface
