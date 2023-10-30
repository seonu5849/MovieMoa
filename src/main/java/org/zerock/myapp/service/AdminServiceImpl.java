package org.zerock.myapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.*;
import org.zerock.myapp.mapper.AdminMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;
    private int perPage = 5;

    @Override
    public Integer offset(Integer pageNum){ // 페이징 처리를 통해 해당되는 레코드를 출력
        return (pageNum - 1) * perPage;
    }

    @Override
    public List<MemberVO> findAllMember(Integer pageNum) { // 전체 회원 조회
        log.trace("findAllMember({}) invoked.", pageNum);

        Integer offset = offset(pageNum);
        log.info("\t+ offset: {}, perPage: {}", offset, perPage);

        return this.adminMapper.selectAllMember(offset, perPage);
    }

    @Override
    public List<MemberVO> searchMemberNameOrEmail(Integer pageNum, String searchOption, String searchValue) { // 검색기능 (이름, 이메일)
        log.trace("searchMemberNameOrEmail({}, {}) invoked", searchOption, searchValue);

        int offset = offset(pageNum);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("searchOption", searchOption);
        parameters.put("searchValue", searchValue);

        return this.adminMapper.selectMemberNameOrEmail(parameters, offset, perPage);
    }

    @Override
    public MemberVO findDetailMember(Long id) { // 회원 상세정보 서비스
        log.trace("findDetailMember({}) invoked", id);

        MemberDTO dto = new MemberDTO();
        dto.setId(id);

        return this.adminMapper.selectDetailMember(dto);
    }

    @Override
    public Integer joinMember(MemberDTO dto) { // 회원가입 서비스
        log.trace("joinMember({}) invoked", dto);

        return this.adminMapper.insertMember(dto);
    }

    @Override
    public Integer editMember(Long id, String nickname, String address, String phoneNum, String role) { // 회원수정 서비스
        log.trace("editMember({}, {}, {}, {}, {}) invoked", id, nickname, address, phoneNum, role);

        MemberDTO dto = new MemberDTO();
        dto.setId(id);
        dto.setNickname(nickname);
        dto.setAddress(address);
        dto.setPhoneNum(phoneNum);
        if(role.equals("ADMIN")) {
            dto.setRole(Role.ROLE_ADMIN);
        }else{
            dto.setRole(Role.ROLE_MEMBER);
        }

        return this.adminMapper.updateMember(dto);
    }

    @Override
    public Integer deleteMember(Long[] selectedMembers) { // 회원삭제 서비스
        log.trace("deleteMember({}) invoked", Arrays.toString(selectedMembers));

        MemberDTO dto = new MemberDTO();
        for(Long selectedMember : selectedMembers) {
            log.info("\t+ selectedMember : {}", selectedMember);
            dto.setId(selectedMember);
        }
        return this.adminMapper.deleteMember(dto);
    }

    @Override
    public Integer totalPage() {
        log.trace("totalMemberCount() invoked");
        int totalPage = this.adminMapper.totalMemberCount()/perPage;
        if(this.adminMapper.totalMemberCount() % perPage != 0){
            totalPage++;
        }

        return totalPage;
    }

    @Override
    public Integer totalSearchPage(String searchOption, String searchValue) {
        log.trace("totalSearchPage({}, {})", searchOption, searchValue);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("searchOption", searchOption);
        parameters.put("searchValue", searchValue);

        Integer totalPage = this.adminMapper.selectTotalSearchCount(parameters)/perPage;

        if(this.adminMapper.selectTotalSearchCount(parameters) % perPage != 0){
            totalPage++;
        }

        return totalPage;
    }

    @Override
    public Integer totalReportBoardCount() {
        log.trace("totalReportBoardCount() invoked");

        int totalPage = this.adminMapper.totalReportBoardCount() / perPage;

        if(this.adminMapper.totalReportBoardCount() % perPage != 0){
            totalPage++;
        }

        return totalPage;
    }

    @Override
    public Integer totalReportReplyCount() {
        log.trace("totalReportReplyCount() invoked");

        Integer totalPage = this.adminMapper.totalReportReplyCount() / perPage;

        if(this.adminMapper.totalReportReplyCount() % perPage != 0){
            totalPage++;
        }

        return totalPage;
    }

    @Override
    public List<ReportBoardsVO> findReportedBoards(Integer pageNum) {
        log.trace("findReportedBoards({}) invoked.", pageNum);

        Integer offset = offset(pageNum);
        return this.adminMapper.selectReportedBoards(offset, perPage);
    }

    @Override
    public List<ReportReplyVO> findReportedReply(Integer pageNum) {
        log.trace("findReportedReply({}) invoked.", pageNum);

        Integer offset = offset(pageNum);
        return this.adminMapper.selectReportedReply(offset, perPage);
    }

    @Override
    public List<BoardVO> findMemberByBoard(Long memberId, Integer pageNum) {
        log.trace("findMemberByBoard({}) invoked.", pageNum);

        Integer offset = offset(pageNum);
        return this.adminMapper.selectMemberByBoard(memberId, offset, perPage);
    }

    @Override
    public Integer deleteMemberByBoard(Long[] ids) {
        log.trace("deleteMemberByBoard({}) invoked.", Arrays.toString(ids));

        Integer affectedRows = 0;

        for(Long id : ids){
            affectedRows += this.adminMapper.deleteMemberByBoard(id);
        }

        return affectedRows;
    }

    @Override
    public Integer totalMemberByBoardCount(Long memberId) {
        log.trace("totalEventCount({}) invoked.", memberId);

        Integer totalPages = this.adminMapper.totalMemberByBoardCount(memberId) / perPage;

        if(this.adminMapper.totalMemberByBoardCount(memberId) % perPage != 0){
            totalPages++;
        }

        return totalPages;
    }

    @Override
    public List<EventsVO> findAllEvents(Integer pageNum) {
        log.trace("findAllEvents({}) invoked.", pageNum);

        Integer offset = offset(pageNum);
        return this.adminMapper.selectAllEvents(offset, perPage);
    }

    @Override
    public Integer deleteEvents(Long[] ids) {
        log.trace("deleteEvents({}) invoked.", Arrays.toString(ids));
        Integer affectedRows = 0;

        for(Long id : ids) {
            EventsDTO dto = new EventsDTO();
            dto.setId(id);

            affectedRows += this.adminMapper.deleteEvent(dto);
        }

        return affectedRows;
    }

    @Override
    public Integer totalEventCount() {
        log.trace("totalEventCount() invoked");
        int totalPage = this.adminMapper.totalEventCount()/perPage;
        if(this.adminMapper.totalEventCount() % perPage != 0){
            totalPage++;
        }

        return totalPage;
    }

    @Override
    public List<StoreVO> findAllProducts(Integer pageNum) {
        log.trace("findAllProducts({}) invoked.", pageNum);

        Integer offset = offset(pageNum);
        return this.adminMapper.selectAllProduct(offset, perPage);
    }

    @Override
    public Integer deleteProducts(Long[] ids) {
        log.trace("deleteProducts({}) invoked.", Arrays.toString(ids));
        Integer affectedRows = 0;

        for(Long id : ids){
            StoreDTO dto = new StoreDTO();
            dto.setId(id);

            affectedRows += this.adminMapper.deleteProduct(dto);
        }

        return affectedRows;
    }

    @Override
    public Integer totalProductCount() {
        log.trace("totalProductCount() invoked");

        Integer totalPage = this.adminMapper.totalProductCount()/perPage;

        if(this.adminMapper.totalProductCount() % perPage != 0){
            totalPage++;
        }

        return totalPage;
    }

    @Override
    public Integer totalInquiriesCount() {
        log.trace("totalInquiriesCount() invoked");
        Integer totalPage = this.adminMapper.totalInquiriesCount()/perPage;
        if(this.adminMapper.totalInquiriesCount() % perPage != 0){
            totalPage++;
        }

        return totalPage;
    }

    @Override
    public List<InquiriesVO> findAllInquiries(Integer pageNum) {
        log.trace("findAllEvents({}) invoked.", pageNum);

        Integer offset = offset(pageNum);
        return this.adminMapper.selectAllInquiries(offset, perPage);
    }

    @Override
    public InquiriesVO findByMemberInquiries(Long id) {
        log.trace("findByMemberInquiries({}) invoked.", id);

        return this.adminMapper.selectMemberInquiries(id);
    }

    @Override
    public Integer addInquiriesResponse(Long id, Long adminId, String inquiryResponsesContent) {
        log.trace("addInquiriesResponse({}, {}, {}) invoked.", id, adminId, inquiryResponsesContent);


        InquiryResponsesDTO dto = new InquiryResponsesDTO();
        dto.setId(id);
        dto.setAdminId(adminId);
        dto.setResponsesContent(inquiryResponsesContent);

        return this.adminMapper.insertInquiriesResponse(dto);
    }


} // end class
