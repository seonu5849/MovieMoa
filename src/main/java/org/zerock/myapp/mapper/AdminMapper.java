package org.zerock.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.zerock.myapp.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMapper {

    // 전체 회원 조회: 단순 출력
    public abstract List<MemberVO> selectAllMember();

    // 전체 회원 조회: 페이징 처리를 포함해 전체 회원 목록을 조회
    public abstract List<MemberVO> selectAllMemberPaging(@Param("offset") Integer offset, @Param("perPage") Integer perPage);

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

//    ============================================================================================

    // 전체 신고된 게시글의 개수 조회
    public abstract Integer totalReportBoardCount();

    // 전체 신고된 댓글의 개수 조회
    public abstract Integer totalReportReplyCount();

    // 해당 페이지의 신고된 게시글 목록 조회
    public abstract List<ReportBoardsVO> selectReportedBoards(@Param("offset") Integer offset, @Param("perPage") Integer perPage);

    // 해당 페이지의 신고된 댓글 목록 조회
    public abstract List<ReportReplyVO> selectReportedReply(@Param("offset") Integer offset, @Param("perPage") Integer perPage);

    // 회원에 대한 처분(상태)를 업데이트
    public abstract Integer updateMemberStatus(@Param("memberId") Long memberId, // 게시글 작성자 - 신고 받은 사람
                                               @Param("role") Role role,
                                               @Param("status") String Status,
                                               @Param("suspensionPeriod") LocalDate suspensionPeriod);

    // 신고 게시물 처리 유무 업데이트
    public abstract Integer updateReportBoardComplete(@Param("memberId") Long memberId, // 게시글 작성자 - 신고 받은 사람
                                                      @Param("boardId") Long boardId, // 신고받은 게시물의 번호
                                                      @Param("result") String status);
    // 신고 댓글 처리 유무 업데이트
    public abstract Integer updateReportReplyComplete(@Param("memberId") Long memberId,
                                                      @Param("replyId") Long replyId,
                                                      @Param("result") String status);

    // 신고게시글의 처분을 변경하기 위한 ReportBoards 업데이트
    public abstract Integer updateReportBoardResult(@Param("reportId") Long reportId,
                                                    @Param("reasonForChange") String reasonForChange,
                                                    @Param("result") String result);

    // 신고게시글의 처분을 변경하기 위한 ReportBoards 업데이트
    public abstract Integer updateReportReplyResult(@Param("reportId") Long reportId,
                                                    @Param("reasonForChange") String reasonForChange,
                                                    @Param("result") String result);

    // 신고게시글의 처분을 수정하기 위한 select 쿼리
    public abstract ReportBoardsVO selectReportedBoardsResult(@Param("reportId") Long reportId,
                                                                    @Param("writerId") Long writerId);

    // 신고댓글의 처분을 수정하기 위한 select 쿼리
    public abstract ReportReplyVO selectReportedReplyResult(@Param("reportId") Long reportId,
                                                              @Param("writerId") Long writerId);

//    ============================================================================================

    // 페이징 처리된 멤버의 게시글 출력
    public abstract List<BoardVO> selectMemberByBoard(@Param("memberId") Long memberId, @Param("offset") Integer offset, @Param("perPage") Integer perPage);

    // 특정 게시글 ID의 배열에 해당하는 게시글 삭제
    public abstract Integer deleteMemberByBoard(@Param("id") Long id);

    // 페이징을 위한 특정 멤버의 게시글 총 레코드 수 조회
    public abstract Integer totalMemberByBoardCount(@Param("memberId") Long memberId);

//    ============================================================================================

    // 해당 페이지의 이벤트 목록 조회
    public abstract List<EventsVO> selectAllEvents(@Param("offset") Integer offset, @Param("perPage") Integer perPage);

    // 이벤트 추가
    public abstract Integer insertEvent(@Param("event") EventsVO event);

    // 이벤트 수정
    public abstract Integer updateEvent(@Param("event") EventsVO event);

    // 지정된 이벤트들 삭제
    public abstract Integer deleteEvent(@Param("event") EventsDTO event);

    // 전체 이벤트 개수 조회
    public abstract Integer totalEventCount();

//    ============================================================================================

    // 해당 페이지의 모든 제품 목록 조회
    public abstract List<StoreVO> selectAllProduct(@Param("offset") Integer offset, @Param("perPage") Integer perPage);

    // 특정 제품 ID의 배열에 해당하는 제품 삭제
    public abstract Integer deleteProduct(@Param("dto") StoreDTO dto);

    // 전체 제품의 개수 조회
    public abstract Integer totalProductCount();

//    ============================================================================================

    // 전체 문의 개수 조회
    public abstract Integer totalInquiriesCount();

    // 해당 페이지의 문의 목록 조회
    public abstract List<InquiriesVO> selectAllInquiries(@Param("offset") Integer offset, @Param("perPage") Integer perPage);

    // 특정 회원의 문의 정보 문의 번호로 조회
    public abstract InquiriesVO selectMemberInquiries(@Param("id") Long id);

    // 문의에 대한 답변 추가
    public abstract Integer insertInquiriesResponse(@Param("dto") InquiryResponsesDTO dto);

} // end interface
