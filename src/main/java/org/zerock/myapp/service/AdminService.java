package org.zerock.myapp.service;

import org.zerock.myapp.domain.*;

import java.util.List;

public interface AdminService {

    // 페이지 번호에 따른 데이터 시작점 계산
    public abstract Integer offset(Integer pageNum);

    // 매개변수 없는 전체 회원 조회
    public abstract List<MemberVO> findAllMember();

    // 전체 회원 조회: 페이징 처리를 포함해 전체 회원 목록을 조회
    public abstract List<MemberVO> findAllMember(Integer pageNum);

    // 특정 이름 또는 이메일로 회원 검색: 이름 또는 이메일로 회원을 검색. 페이징 처리 포함.
    public abstract List<MemberVO> searchMemberNameOrEmail(Integer pageNum, String searchOption, String searchValue);

    // 회원 상세 정보 조회: 특정 회원의 상세 정보를 조회
    public abstract MemberVO findDetailMember(Long id);

    // 회원 추가: 새로운 회원을 데이터베이스에 추가
    public abstract Integer joinMember(MemberDTO dto);

    // 회원 정보 업데이트: 기존 회원의 정보를 업데이트
    public abstract Integer editMember(Long id, String nickname, String address, String phoneNum, String role);

    // 회원 삭제: 특정 회원을 데이터베이스에서 삭제
    public abstract Integer deleteMember(Long[] selectedMembers);

    // 총 회원 수 조회: 데이터베이스에 있는 전체 회원 수를 조회
    public abstract Integer totalPage();

    // 검색 조건에 따른 회원 수 조회: 이름, 이메일, 또는 닉네임으로 검색된 회원의 수를 조회
    public abstract Integer totalSearchPage(String searchOption, String searchValue);

//    ====================================================================================================
    // 전체 신고된 게시글의 개수 조회
    public abstract Integer totalReportBoardCount();

    // 전체 신고된 댓글의 개수 조회
    public abstract Integer totalReportReplyCount();

    // 해당 페이지의 신고된 게시글 목록 조회
    public abstract List<ReportBoardsVO> findReportedBoards(Integer pageNum);

    // 해당 페이지의 신고된 댓글 목록 조회
    public abstract List<ReportReplyVO> findReportedReply(Integer pageNum);

    // 회원에 대한 처분(상태)를 업데이트
    public abstract Integer editMemberStatus(Long memberId, String status);

    // 신고게시글의 처분 여부를 업데이트
    public abstract void editBoardComplete(Long memberId, Long boardId, String status);

    // 신고댓글의 처분 여부를 업데이트
    public abstract void editReplyComplete(Long memberId, Long replyId, String status);

    // 신고 게시글 한번 처분된것을 수정 업데이트
    public abstract Integer modifyMemberAndReport(Long memberId, Long reportBoardId, String newResult, String reasonForChange);

//    ====================================================================================================
    // 페이징 처리된 멤버의 게시글 출력
    public abstract List<BoardVO> findMemberByBoard(Long memberId, Integer pageNum);

    // 특정 게시글 ID의 배열에 해당하는 게시글 삭제
    public abstract Integer deleteMemberByBoard(Long[] ids);

    // 페이징을 위한 특정 멤버의 게시글 총 레코드 수 조회
    public abstract Integer totalMemberByBoardCount(Long memberId);

//    ====================================================================================================
    // 해당 페이지의 이벤트 목록 조회
    public abstract List<EventsVO> findAllEvents(Integer pageNum);

    // 지정된 이벤트들 삭제
    public abstract Integer deleteEvents(Long[] ids);

    // 전체 이벤트 개수 조회
    public abstract Integer totalEventCount();

//    ====================================================================================================
    // 해당 페이지의 모든 제품 목록 조회
    public abstract List<StoreVO> findAllProducts(Integer pageNum);

    // 특정 제품 ID의 배열에 해당하는 제품 삭제
    public abstract Integer deleteProducts(Long[] ids);

    // 전체 제품의 개수 조회
    public abstract Integer totalProductCount();

    //    ====================================================================================================
    // 전체 문의 개수 조회
    public abstract Integer totalInquiriesCount();

    // 해당 페이지의 문의 목록 조회
    public abstract List<InquiriesVO> findAllInquiries(Integer pageNum);

    // 특정 회원의 문의 정보 문의 번호로 조회
    public abstract InquiriesVO findByMemberInquiries(Long id);

    // 문의에 대한 답변 추가
    public abstract Integer addInquiriesResponse(Long id, Long adminId, String inquiryResponsesContent);

} // end interface
