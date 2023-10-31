package org.zerock.myapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.myapp.domain.*;
import org.zerock.myapp.service.AdminService;
import org.zerock.myapp.service.MovieJsonService;
import org.zerock.myapp.service.MovieService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/admin/*")
@Controller
public class AdminController {

    private final AdminService adminService;
    private final MovieService movieService;
    private final MovieJsonService movieJsonService;

    // 회원목록 및 검색을 위한 메소드
    @GetMapping("/memberList/{pageNum}")
    public String memberListSearch(String searchValue, @PathVariable(value = "pageNum") Integer pageNum, String searchOption, Model model){
        log.trace("memberListSearch({}, {}, {}) invoked.", pageNum, searchOption, searchValue);

        // 회원 정보를 담을 리스트와 전체 페이지 수를 저장할 변수 선언
        List<MemberVO> list = null;
        Integer totalPages = 0;

        // 검색 옵션과 검색값이 모두 null인 경우 (검색하지 않았을 때)
        if(searchOption == null && searchValue == null){
            // 현재 페이지 번호에 따라 전체 회원 목록 조회
            list = this.adminService.findAllMember(pageNum);
            // 전체 페이지 수 조회
            totalPages = this.adminService.totalPage();
        }

        // 검색 옵션과 검색값이 모두 존재하는 경우 (검색을 했을 때)
        if(searchOption != null && searchValue != null){
            // 검색 옵션과 값에 따라 회원 목록 조회
            list  = this.adminService.searchMemberNameOrEmail(pageNum, searchOption, searchValue);
            // 검색 결과에 따른 전체 페이지 수 조회
            totalPages = this.adminService.totalSearchPage(searchOption, searchValue);

            // 모델에 검색 옵션과 값 추가
            model.addAttribute("searchOption", searchOption);
            model.addAttribute("searchValue", searchValue);
        }

        // 모델에 현재 페이지 번호, 회원 목록, 전체 페이지 수 추가
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("members", list);
        model.addAttribute("totalPages", totalPages);

        // "admin/memberList" 페이지로 이동
        return "admin/memberList";
    } // memberListSearch


    @PostMapping("/memberList") // 회원 선택(일괄) 삭제
    public String memberListDelete(@RequestParam("selectedMembers") Long[] selectedMembers){
        log.trace("memberListDelete({}) invoked.", Arrays.toString(selectedMembers));

        this.adminService.deleteMember(selectedMembers);

        return "redirect:/admin/memberList/1";
    } // memberListSearch

    @GetMapping("/detailMember/{id}") // 상세정보
    public String detailMemberView(@PathVariable("id") Long id, Model model){
        log.trace("detailMemberView() invoked.");

        MemberVO foundMember = this.adminService.findDetailMember(id);
        Integer totalPages = this.adminService.totalMemberByBoardCount(id);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if(foundMember.getBirthday() != null){
            String formattedDate = formatter.format(foundMember.getBirthday());
            model.addAttribute("birthday", formattedDate);
        }

        model.addAttribute("foundMember", foundMember);
        model.addAttribute("totalPages", totalPages);

        return "admin/detailMember";
    } // detailMemberView

    @GetMapping("/detailMember/boardList/{id}/{pageNum}") // 상세정보/게시판
    public String boardListView(Model model, @PathVariable("id") Long id, @PathVariable("pageNum") Integer pageNum){
        log.trace("boardListView({}, {}) invoked.", id, pageNum);

        List<BoardVO> list = this.adminService.findMemberByBoard(id, pageNum);
        Integer totalPages = this.adminService.totalMemberByBoardCount(id);

        if(!list.isEmpty()) {
            model.addAttribute("userName", list.get(0).getNickname());
        }
        model.addAttribute("memberId", id);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("boards", list);
        model.addAttribute("totalPages", totalPages);

        return "/admin/boardList";
    } // boardListView

    @PostMapping("/detailMember/boardList") // 상세정보/게시판 - 일괄삭제
    public String boardListDelete(@RequestParam("selectedMembers") Long[] selectedMembers, @RequestParam("id") Long id){
        log.trace("boardListDelete({}, {}) invoked.", id, Arrays.toString(selectedMembers));

        Integer affectedRows = this.adminService.deleteMemberByBoard(selectedMembers);

        return "redirect:/admin/detailMember/boardList/"+id+"/1";
    } // boardListView

    @GetMapping("/detailMember/modifyMember/{id}") // 상세정보/수정페이지
    public String modifyMemberView(@PathVariable("id") Long id, Model model){
        log.trace("modifyMemberView() invoked.");

        MemberVO foundMember = this.adminService.findDetailMember(id);

        model.addAttribute("foundMember", foundMember);

        return "/admin/modifyMember";
    } // boardListView

    @PostMapping("/detailMember/modifyMember") // 상세정보/수정페이지 - 수정
    public String modifyMemberUpdate(Long id, String nickname, String address, String phoneNum, String role){
        log.trace("modifyMemberUpdate({}, {}, {}, {}, {}) invoked.",id, nickname, address, phoneNum, role);

        this.adminService.editMember(id, nickname, address, phoneNum, role);

        return "redirect:/admin/detailMember/"+id;
    } // boardListView

    @GetMapping("/event/{pageNum}") // 이벤트
    public String eventView(@PathVariable(value = "pageNum") Integer pageNum, String searchOption, Model model){
        log.trace("eventView() invoked.");

        List<EventsVO> list = this.adminService.findAllEvents(pageNum);
        Integer totalPages = this.adminService.totalEventCount();

        model.addAttribute("evnets", list);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", totalPages);

        return "/admin/event";
    } // eventView

    @PostMapping("/event")
    public String eventDelete(@RequestParam("selectedMembers") Long[] selectedMembers){ // 이벤트 삭제
        log.trace("eventDelete() invoked");

        Integer affectedRows = this.adminService.deleteEvents(selectedMembers);
        log.info("\t+ affectedRows : {}", affectedRows);

        return "redirect:/admin/event/1";
    } // eventDelete

    @GetMapping("/product/{pageNum}") // 상품
    public String productView(@PathVariable(value = "pageNum") Integer pageNum, Model model){
        log.trace("productView({}) invoked.", pageNum);

        List<StoreVO> list = this.adminService.findAllProducts(pageNum);
        Integer totalPages = this.adminService.totalProductCount();

        model.addAttribute("products", list);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", totalPages);

        return "/admin/product";
    } // productView

    @PostMapping("/product") // 상품
    public String productDelete(@RequestParam("selectedMembers") Long[] selectedMembers){
        log.trace("productDelete({}) invoked.", Arrays.toString(selectedMembers));

        Integer affectedRows = this.adminService.deleteProducts(selectedMembers);

        return "redirect:/admin/product/1";
    } // productDelete

    @GetMapping("/inquiry/{pageNum}")
    public String inquiryView(@PathVariable(value = "pageNum") Integer pageNum, Model model){ // 문의 목록
        log.trace("inquiryView() invoked.");

        List<InquiriesVO> list = this.adminService.findAllInquiries(pageNum);
        Integer totalPages = this.adminService.totalInquiriesCount();

        model.addAttribute("inquiries", list);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", totalPages);

        return "/admin/inquiry";
    } // inquiryView

    @GetMapping("/inquiryAnswer/{inquiriesId}")
    public String inquiryAnswerView(@PathVariable("inquiriesId") Long id, Model model){ // 문의 답변
        log.trace("inquiryAnswerView() invoked.");

        InquiriesVO inquirie = this.adminService.findByMemberInquiries(id);

        model.addAttribute("inquirie", inquirie);

        return "/admin/inquiryAnswer";
    } // inquiryAnswerView

    @PostMapping("/inquiryAnswer")
    public String inquiryAnswerWrite(Long inquiryId, Long memberId,  @RequestParam("inquiryResponsesContent") String responseContent){ // 문의 답변
        log.trace("inquiryAnswerWrite({}, {}, {}) invoked.",inquiryId, memberId, responseContent);

        Integer affectedRows = this.adminService.addInquiriesResponse(inquiryId, memberId, responseContent);

        return "redirect:inquiry/1";
    } // inquiryAnswerWrite

    @GetMapping("/boardComplaint/{pageNum}") // 신고 게시글
    public String boardComplaintView(@PathVariable(value = "pageNum") Integer pageNum, Model model){
        log.trace("boardComplaintView({}) invoked.", pageNum);

        List<ReportBoardsVO> list = this.adminService.findReportedBoards(pageNum);
        Integer totalPages = this.adminService.totalReportBoardCount();

        model.addAttribute("reportBoards", list);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", totalPages);

        return "/admin/boardComplaint";
    } // boardComplaintView

    @PostMapping("/boardComplaint") // 신고 게시글
    public String boardComplaintTask(){
        log.trace("boardComplaintTask() invoked.");

        return "redirect:/admin/boardComplaint";
    } // boardComplaintTask

    @GetMapping("/replyComplaint/{pageNum}") // 신고 댓글
    public String replyComplaintView(@PathVariable(value = "pageNum") Integer pageNum, Model model){
        log.trace("replyComplaintView({}) invoked.", pageNum);

        List<ReportReplyVO> list = this.adminService.findReportedReply(pageNum);
        Integer totalPages = this.adminService.totalReportReplyCount();

        list.forEach(log::info);

        model.addAttribute("reportReplys", list);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", totalPages);

        return "/admin/replyComplaint";
    } // replyComplaintView

    @PostMapping("/replyComplaint") // 신고 댓글
    public String replyComplaintTask(){
        log.trace("replyComplaintTask() invoked.");

        return "redirect:/admin/replyComplaint";
    } // replyComplaintTask

    // 영화 데이터 가져오기
    @GetMapping("/movieData")
    public String moviesDataView(Model model) {
        log.trace("moviesDataView() invoked.");

        return "/admin/movieData";
    } // moviesData

    // 영화 데이터 가져오기
    @PutMapping("/movieData")
    public String moviesData(Model model, Long startNum, Long endNum) throws IOException {
        log.trace("moviesData({}, {}) invoked.", startNum, endNum);

        movieJsonService.jsonToGenres();
        movieJsonService.jsonToMovies(startNum, endNum);
        movieJsonService.jsonToMovieCredits(startNum, endNum);
        movieJsonService.jsonToCertification(startNum, endNum);

        model.addAttribute("updateSuccess", true);

        return "/admin/movieData";
    } // moviesData

    // 영화 전체 목록 관리자전용
    @GetMapping("/movieDataList")
    public String movieDataListView(Model model) {
        log.trace("movieDataListView() invoked.");

        List<MovieVO> allMovies = movieService.findAllMoviesmanagerOnly();
        allMovies.forEach(log::info);

        model.addAttribute("moviesList", allMovies);

        return "/admin/movieDataList";
    } // movieDataListView

} // end class
