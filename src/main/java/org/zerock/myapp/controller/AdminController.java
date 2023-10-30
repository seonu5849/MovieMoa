//package org.zerock.myapp.controller;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.zerock.myapp.domain.MemberVO;
//import org.zerock.myapp.service.AdminService;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Log4j2
//@RequiredArgsConstructor
//
//@RequestMapping("/admin/*")
//@Controller
//public class AdminController {
//
//    private final AdminService adminService;
//    private final EventService eventService;
//    private final BoardService boardService;
//    private final ProductService productService;
//    private final InquiriesService inquiriesService;
//    private final AReportedService reportedService;
//
//    @GetMapping("/memberList/{pageNum}") // 회원목록 & 검색
//    public String memberListSearch(String searchValue, @PathVariable(value = "pageNum") Integer pageNum, String searchOption, Model model){
//        log.trace("memberListSearch({}, {}, {}) invoked.", pageNum, searchOption, searchValue);
//
//        List<MemberVO> list = null;
//        Integer totalPages = 0;
//
//        // 검색 옵션과 검색값이 모두 null일 경우, 즉 검색을 하지 않았을 때 실행되는 로직
//        if(searchOption == null && searchValue == null){
//            list = this.adminService.findAllMember(pageNum); // 전체 회원을 페이지에 따라 조회
//            totalPages = this.adminService.totalPage(); // 전체 페이지 수 조회
//        }
//
//        // 검색 옵션과 검색값이 모두 존재할 경우 실행되는 로직
//        if(searchOption != null && searchValue != null){
//            list  = this.adminService.searchMemberNameOrEmail(pageNum, searchOption, searchValue); // 검색 옵션과 값에 따라 회원을 조회
//            totalPages = this.adminService.totalSearchPage(searchOption, searchValue); // 검색 결과에 따른 전체 페이지 수 조회
//
//            model.addAttribute("searchOption", searchOption);
//            model.addAttribute("searchValue", searchValue);
//        }
//
//        model.addAttribute("currentPage", pageNum);
//        model.addAttribute("member",list);
//        model.addAttribute("totalPages", totalPages);
//
//        return "admin/memberList";
//    } // memberListSearch
//
//    @PostMapping("/memberList") // 회원 선택(일괄) 삭제
//    public String memberListDelete(@RequestParam("selectedMembers") Long[] selectedMembers){
//        log.trace("memberListDelete({}) invoked.", Arrays.toString(selectedMembers));
//
//        this.adminService.deleteMember(selectedMembers);
//
//        return "redirect:/admin/memberList/1";
//    } // memberListSearch
//
//    @GetMapping("/detailMember/{id}") // 상세정보
//    public String detailMemberView(@PathVariable("id") Long id, Model model){
//        log.trace("detailMemberView() invoked.");
//
//        MemberVO foundMember = this.adminService.findDetailMember(id);
//        Integer totalPages = this.boardService.totalMemberByBoardCount(id);
//
//        model.addAttribute("foundMember", foundMember);
//        model.addAttribute("totalPages", totalPages);
//
//        return "admin/detailMember";
//    } // detailMemberView
//
//    @GetMapping("/detailMember/boardList/{id}/{pageNum}") // 상세정보/게시판
//    public String boardListView(Model model, @PathVariable("id") Long id, @PathVariable("pageNum") Integer pageNum){
//        log.trace("boardListView({}, {}) invoked.", id, pageNum);
//
//        List<BoardWithMemberVO> list = this.boardService.findMemberByBoard(id, pageNum);
//        Integer totalPages = this.boardService.totalMemberByBoardCount(id);
//
//        if(!list.isEmpty()) {
//            model.addAttribute("userName", list.get(0).getName());
//        }
//        model.addAttribute("memberId", id);
//        model.addAttribute("currentPage", pageNum);
//        model.addAttribute("board", list);
//        model.addAttribute("totalPages", totalPages);
//
//        return "/admin/boardList";
//    } // boardListView
//
//    @PostMapping("/detailMember/boardList") // 상세정보/게시판 - 일괄삭제
//    public String boardListDelete(@RequestParam("selectedMembers") Integer[] selectedMembers, @RequestParam("id") Integer id){
//        log.trace("boardListDelete({}, {}) invoked.", id, Arrays.toString(selectedMembers));
//
//        Integer affectedRows = this.boardService.deleteMemberByBoard(selectedMembers);
//
//        return "redirect:/admin/detailMember/boardList/"+id+"/1";
//    } // boardListView
//
//    @GetMapping("/detailMember/modifyMember/{id}") // 상세정보/수정페이지
//    public String modifyMemberView(@PathVariable("id") Integer id, Model model){
//        log.trace("modifyMemberView() invoked.");
//
//        MemberVO foundMember = this.adminService.findDetailMember(id);
//
//        model.addAttribute("foundMember", foundMember);
//
//        return "/admin/modifyMember";
//    } // boardListView
//
//    @PostMapping("/detailMember/modifyMember") // 상세정보/수정페이지 - 수정
//    public String modifyMemberUpdate(Long id, String nickname, String address, String[] phoneNum, String role){
//        log.trace("modifyMemberUpdate({}, {}, {}, {}, {}) invoked.",id, nickname, address, Arrays.toString(phoneNum), role);
//
//        this.adminService.editMember(id, nickname, address, phoneNum, role);
//
//        return "redirect:/admin/detailMember/"+id;
//    } // boardListView
//
//    @GetMapping("/event/{pageNum}") // 이벤트
//    public String eventView(@PathVariable(value = "pageNum") Integer pageNum, String searchOption, Model model){
//        log.trace("eventView() invoked.");
//
//        List<EventWithMemberVO> list = this.eventService.findAllEvents(pageNum);
//        Integer totalPages = this.eventService.totalEventCount();
//
//        model.addAttribute("evnets", list);
//        model.addAttribute("currentPage", pageNum);
//        model.addAttribute("totalPages", totalPages);
//
//        return "/admin/event";
//    } // eventView
//
//    @PostMapping("/event")
//    public String eventDelete(@RequestParam("selectedMembers") Long[] selectedMembers){ // 이벤트 삭제
//        log.trace("eventDelete() invoked");
//
//        Integer affectedRows = this.eventService.deleteEvents(selectedMembers);
//        log.info("\t+ affectedRows : {}", affectedRows);
//
//        return "redirect:/admin/event/1";
//    } // eventDelete
//
//    @GetMapping("/product/{pageNum}") // 상품
//    public String productView(@PathVariable(value = "pageNum") Integer pageNum, Model model){
//        log.trace("productView({}) invoked.", pageNum);
//
//        List<ProductVO> list = this.productService.findAllProducts(pageNum);
//        Integer totalPages = this.productService.totalProductCount();
//
//        model.addAttribute("products", list);
//        model.addAttribute("currentPage", pageNum);
//        model.addAttribute("totalPages", totalPages);
//
//        return "/admin/product";
//    } // productView
//
//    @PostMapping("/product") // 상품
//    public String productDelete(@RequestParam("selectedMembers") Long[] selectedMembers){
//        log.trace("productDelete({}) invoked.", Arrays.toString(selectedMembers));
//
//        Integer affectedRows = this.productService.deleteProducts(selectedMembers);
//
//        return "redirect:/admin/product/1";
//    } // productDelete
//
//    @GetMapping("/inquiry/{pageNum}")
//    public String inquiryView(@PathVariable(value = "pageNum") Integer pageNum, Model model){ // 문의 목록
//        log.trace("inquiryView() invoked.");
//
//        List<InquiriesWithMemberVO> list = this.inquiriesService.findAllInquiries(pageNum);
//        Integer totalPages = this.inquiriesService.totalInquiriesCount();
//
//        model.addAttribute("inquiries", list);
//        model.addAttribute("currentPage", pageNum);
//        model.addAttribute("totalPages", totalPages);
//
//        return "/admin/inquiry";
//    } // inquiryView
//
//    @GetMapping("/inquiryAnswer/{inquiriesId}")
//    public String inquiryAnswerView(@PathVariable("inquiriesId") Long inquiriesId, Model model){ // 문의 답변
//        log.trace("inquiryAnswerView() invoked.");
//
//        InquiriesWithMemberVO inquirie = this.inquiriesService.findByMemberInquiries(inquiriesId);
//
//        model.addAttribute("inquirie", inquirie);
//
//        return "/admin/inquiryAnswer";
//    } // inquiryAnswerView
//
//    @PostMapping("/inquiryAnswer")
//    public String inquiryAnswerWrite(Long inquiryId, Long memberId,  @RequestParam("inquiryResponsesContent") String responseContent){ // 문의 답변
//        log.trace("inquiryAnswerWrite({}, {}, {}) invoked.",inquiryId, memberId, responseContent);
//
//        Integer affectedRows = this.inquiriesService.addInquiriesResponse(inquiryId, memberId, responseContent);
//
//        return "redirect:inquiry/1";
//    } // inquiryAnswerWrite
//
//    @GetMapping("/boardComplaint/{pageNum}") // 신고 게시글
//    public String boardComplaintView(@PathVariable(value = "pageNum") Integer pageNum, Model model){
//        log.trace("boardComplaintView({}) invoked.", pageNum);
//
//        List<ReportedBoardsVO> list = this.reportedService.findReportedBoards(pageNum);
//        Integer totalPages = this.reportedService.totalReportBoardCount();
//
//        model.addAttribute("reportBoards", list);
//        model.addAttribute("currentPage", pageNum);
//        model.addAttribute("totalPages", totalPages);
//
//        return "/admin/boardComplaint";
//    } // boardComplaintView
//
//    @PostMapping("/boardComplaint") // 신고 게시글
//    public String boardComplaintTask(){
//        log.trace("boardComplaintTask() invoked.");
//
//        return "redirect:/admin/boardComplaint";
//    } // boardComplaintTask
//
//    @GetMapping("/replyComplaint/{pageNum}") // 신고 댓글
//    public String replyComplaintView(@PathVariable(value = "pageNum") Integer pageNum, Model model){
//        log.trace("replyComplaintView({}) invoked.", pageNum);
//
//        List<ReportedReplyVO> list = this.reportedService.findReportedReply(pageNum);
//        Integer totalPages = this.reportedService.totalReportReplyCount();
//
//        list.forEach(log::info);
//
//        model.addAttribute("reportReplys", list);
//        model.addAttribute("currentPage", pageNum);
//        model.addAttribute("totalPages", totalPages);
//
//        return "/admin/replyComplaint";
//    } // replyComplaintView
//
//    @PostMapping("/replyComplaint") // 신고 댓글
//    public String replyComplaintTask(){
//        log.trace("replyComplaintTask() invoked.");
//
//        return "redirect:/admin/replyComplaint";
//    } // replyComplaintTask
//
//} // end class
