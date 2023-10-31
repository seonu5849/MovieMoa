package org.zerock.myapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    } // writeBoardView

    @GetMapping("/detailBoard") //{/detailBoard/boardNum}
    public String detailBoardView() {
        log.trace("detailBoardView() invoked.");

        return "/board/detailBoard";
    } // detailBoardView

    @PostMapping("/reply")
    public String replyWrite(){
        log.trace("replyWrite() invoked.");
        return "redirect:/board/detailBoard";
    } //replyWrite

    @PutMapping ("/reply")
    public String replyEdit(){
        log.trace("replyEdit() invoked.");

        return "redirect:/board/detailBoard";
    } //replyEdit

    @DeleteMapping("/reply")
    public String replyDelete(){
        log.trace("replyDelete() invoked.");

        return "redirect:/board/detailBoard";
    } //replyDelete

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
