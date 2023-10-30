package org.zerock.myapp.controller;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Log4j2
@NoArgsConstructor

@RequestMapping("/board/*")
@Controller
public class BoardController {

    @GetMapping("/boards")
    public String boardView() {
        log.trace("boardView() invoked.");

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
