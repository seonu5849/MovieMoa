package org.zerock.myapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.myapp.domain.BoardReplyVO;
import org.zerock.myapp.domain.BoardVO;
import org.zerock.myapp.service.BoardService;

import java.util.List;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/board/*")
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public String boardView(Model model) {
        log.trace("boardView() invoked.");

        List<BoardVO> boardList = this.boardService.findBoardList();

        model.addAttribute("boards", boardList);

        return "/board/boards";
    } // boardView

    @GetMapping("/writeBoard")
    public String writeBoardView() {
        log.trace("writeBoardView() invoked.");

        return "/board/writeBoard";
    } // writeBoardView

    @PostMapping ("/writeBoard")
    public String writeBoard() {
        log.trace("writeBoard() invoked.");

        return "redirect:/board/detailBoard"; // /board/detailBoard?boardNum=***
    } // writeBoard

    @GetMapping("/updateBoard/{id}")
    public String updateBoardView(Model model, @PathVariable("id") Long id) {
        log.trace("updateBoardView() invoked.");

        return "/board/updateBoard";
    } // updateBoardView

    @PostMapping ("/updateBoard")
    public String updateBoard() {
        log.trace("updateBoard() invoked.");

        return "redirect:/board/detailBoard"; // /board/detailBoard?boardNum=***
    } // updateBoard

    @GetMapping("/detailBoard/{id}") //{/detailBoard/boardNum}
    public String detailBoardView(Model model, @PathVariable("id") Long id) {
        log.trace("detailBoardView({}) invoked.", id);

        BoardVO board = this.boardService.findBoard(id);
        List<BoardReplyVO> boardReplyList = this.boardService.findBoardReplyList(id);

        model.addAttribute("board", board);
        model.addAttribute("boardReplyList", boardReplyList);

        return "/board/detailBoard";
    } // detailBoardView

    @PostMapping ("/comment")
    public String writereply(Model model, String content, Long memberId, Long id) {
        log.trace("writereply({}, {}, {}) invoked.", content, memberId, id);

        Integer insertReply = this.boardService.insertReply(content, memberId, id);

        log.info("\t+ insertReply: {}", insertReply);

        return "redirect:/board/detailBoard"; // /board/detailBoard?boardNum=***
    } // writereply

    // 댓글 수정 폼을 보여주는 메서드
    @GetMapping("/board/commentEdit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.trace("showEditForm({}) invoked.", id);

        BoardReplyVO boardReply = this.boardService.findBoardReply(id);

        // 조회한 댓글 정보를 모델에 추가
        model.addAttribute("reply", boardReply);

        // 수정 폼이 포함된 뷰 반환
        return "/board/detailBoard"; // 해당 뷰 이름으로 수정 필요
    }

    // 댓글을 실제로 수정하는 메서드
    @PutMapping("/board/commentEdit/{id}")
    public String editComment(@PathVariable Long id, BoardReplyVO reply) {
        log.trace("editComment({}, {}) invoked.", id, reply);

        Integer updated = this.boardService.updateBoardReply(reply);

        log.info("\t+ updated: {}", updated);

        // 댓글 목록이나 상세보기 페이지 등으로 리다이렉트
        return "redirect:/board/detailBoard";
    }

    @GetMapping("/reportBoard")
    public String reportBoardView() {
        log.trace("reportBoardView() invoked.");

        return "/board/reportBoard";
    } // reportBoardView

    @PostMapping("/reportBoard")
    public String reportBoardWrite() {
        log.trace("reportBoardWrite() invoked.");

        return "redirect:/board/detailBoard";
    } // reportReplyWrite

    @GetMapping("/reportReply")
    public String reportReplyView() {
        log.trace("reportReplyView() invoked.");

        return "/board/reportReply";
    } // reportReplyView

    @PostMapping("/reportReply")
    public String reportReplyWrite() {
        log.trace("reportReplyWrite() invoked.");

        return "redirect:/board/detailBoard";
    } // reportReplyWrite

} // end class
