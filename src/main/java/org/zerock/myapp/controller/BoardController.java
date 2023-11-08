package org.zerock.myapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.myapp.domain.*;
import org.zerock.myapp.service.BoardService;

import java.util.*;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/board/*")
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards/{pageNum}")
    public String boardView(Model model, @PathVariable("pageNum") Integer pageNum) {
        log.trace("boardView({}) invoked.", pageNum);

        List<BoardAndReplyCntVO> boardList = this.boardService.findBoardList(pageNum);
        Map<String, Integer> paginationInfo = this.boardService.totalBoardListCnt();
        int totalItems = paginationInfo.get("totalItems");
        int totalPages = paginationInfo.get("totalPages");

        model.addAttribute("boards", boardList);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);

        return "/board/boards";
    } // boardView

    @GetMapping("/writeBoard")
    public String writeBoardView(Model model) {
        log.trace("writeBoardView() invoked.");

        List<BoardKategoriesVO> kategoriesList = this.boardService.findBoardKategoriesList();

        model.addAttribute("kategoriesList", kategoriesList);

        return "/board/writeBoard";
    } // writeBoardView

    // 영화 검색 엔드포인트
    @GetMapping("/search/movies")
    public @ResponseBody List<MovieVO> searchMovies(@RequestParam("term") String term) {

        // 검색어에 따라 영화를 찾고 MovieVO 객체 리스트를 반환
        List<MovieVO> searchResults = this.boardService.searchMovies(term);

        // 검색 결과를 JSON 형태로 반환합니다.
        return searchResults;
    }

    @PostMapping ("/writeBoard")
    public String writeBoard(String title, String content, Long kategorieId, Long movieId) {
        log.trace("writeBoard({}, {}, {}, {}) invoked.", title, content, kategorieId, movieId);

        // 현재 인증된 사용자의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증된 사용자의 이름(여기서는 사용자 ID로 사용)을 가져옵니다.
        String username = authentication.getName();
        // 사용자 이름(아이디)를 Long 타입으로 변환합니다.
        Long memberId = Long.valueOf(username);

        if (movieId != null) {
            Integer posted = this.boardService.postWriting(title, content, kategorieId, movieId, memberId);
        } else {
            Integer posted = this.boardService.postWriting(title, content, kategorieId, null, memberId);
        }

        return "redirect:/board/boards/1";
    } // writeBoard

    @GetMapping("/updateBoard/{id}")
    public String updateBoardView(Model model, @PathVariable("id") Long id) {
        log.trace("updateBoardView() invoked.");

        BoardVO board = this.boardService.findBoard(id);
        log.info("\t+ board: {}", board);

        model.addAttribute("board", board);

        return "/board/updateBoard";
    } // updateBoardView

    @PutMapping ("/updateBoard")
    public String updateBoard(Long id, String title, String content, Long kategorieId, Long movieId) {
        log.trace("updateBoard({}, {}, {}, {}, {}) invoked.", id, title, content, kategorieId, movieId);

        // 현재 인증된 사용자의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증된 사용자의 이름(여기서는 사용자 ID로 사용)을 가져옵니다.
        String username = authentication.getName();
        // 사용자 이름(아이디)를 Long 타입으로 변환합니다.
        Long memberId = Long.valueOf(username);

        if (movieId != null) {
            Integer updated = this.boardService.updateBoard(id, title, content, kategorieId, movieId, memberId);
        } else {
            Integer updated = this.boardService.updateBoard(id, title, content, kategorieId, null, memberId);
        }

        return "redirect:/board/detailBoard/" + id;
    } // updateBoard

    @DeleteMapping("/deleteAPost/{id}")
    public ResponseEntity<?> deleteAPost(@PathVariable("id") Long id) {
        log.trace("deleteAPost({}) invoked.", id);

        Integer deleted = this.boardService.deleteAPost(id);
        log.info("\t+ deleted: {}", deleted);

        // 리다이렉트할 URL을 JSON 객체로 반환
        return ResponseEntity.ok(Collections.singletonMap("redirectUrl", "/board/boards/1"));
    }

    @GetMapping("/detailBoard/{id}") //{/detailBoard/boardNum}
    public String detailBoardView(Model model, @PathVariable("id") Long id) {
        log.trace("detailBoardView({}) invoked.", id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            log.info("\t+ 인증된 사용자");
            String username = authentication.getName();
            Long memberId = Long.valueOf(username);
            model.addAttribute("currentUserId", memberId);
        }

        Integer viewCountUp = this.boardService.viewCountUp(id);
        BoardVO board = this.boardService.findBoard(id);
        List<BoardReplyVO> boardReplyList = this.boardService.findBoardReplyList(id);

        model.addAttribute("board", board);
        model.addAttribute("boardReplyList", boardReplyList);

        return "/board/detailBoard";
    } // detailBoardView

    @PostMapping("/like/{boardId}")
    public ResponseEntity<?> toggleLike(@PathVariable Long boardId) {
        log.trace("toggleLike({}) invoked.", boardId);
        // 현재 인증된 사용자의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증된 사용자의 이름(여기서는 사용자 ID로 사용)을 가져옵니다.
        String username = authentication.getName();
        // 사용자 이름(아이디)를 Long 타입으로 변환합니다.
        Long memberId = Long.valueOf(username);

        try {
            // 좋아요 상태 체크 (좋아요 안누른 상태 = false)
            boolean likesCheck = boardService.likescheck(boardId, memberId);
            log.info("\t+ likescheck: {}", likesCheck);

            if (!likesCheck) {
                log.info("\t+ 좋아요 누르지 않음, 좋아요 추가");
                // 좋아요를 누르지 않았다면, 좋아요 추가
                boardService.addLikes(boardId, memberId);
                boardService.LikesCountUp(boardId);
            } else {
                log.info("\t+ 좋아요 이미 누름, 좋아요 취소");
                // 좋아요를 이미 눌렀다면, 좋아요 취소
                boardService.cancelLikes(boardId, memberId);
                boardService.LikesCountDown(boardId);
            }

            // 업데이트된 좋아요 수 조회
            Integer likeCount = boardService.getLikeCount(boardId);

            Map<String, Object> response = new HashMap<>();
            response.put("liked", !likesCheck);
            response.put("likeCount", likeCount);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping ("/comment")
    public String writereply(Model model, String content, Long memberId, Long id) {
        log.trace("writereply({}, {}, {}) invoked.", content, memberId, id);

        Integer insertReply = this.boardService.insertReply(content, memberId, id);

        log.info("\t+ insertReply: {}", insertReply);

        return "redirect:/board/detailBoard/"+ id;
    } // writereply

    // 댓글 수정 폼을 보여주는 메서드
    @GetMapping("/getReplyContent/{replyId}")
    public ResponseEntity<?> getReplyContentView(@PathVariable("replyId") Long replyId) {
        log.trace("getReplyContent({}) invoked.", replyId);
        try {
            // 댓글 정보 불러와서 리턴하기
            BoardReplyVO boardReply = boardService.findBoardReply(replyId);
            log.info("\t+ boardReply: {}", boardReply);
            return ResponseEntity.ok().body(Collections.singletonMap("reply", boardReply));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 내용을 불러오는데 실패했습니다.");
        }
    }

    @PutMapping("/commentEdit")
    public String editComment(Long id, String content) {
        log.trace("editComment({}, {}) invoked.", id, content);

        Integer updated = this.boardService.updateBoardReply(id, content);
        BoardReplyVO boardReply = this.boardService.findBoardReply(id);

        log.info("\t+ updated: {}", updated);

        // 댓글 목록이나 상세보기 페이지 등으로 리다이렉트
        return "redirect:/board/detailBoard/" + boardReply.getBoardId();
    }

    @DeleteMapping("/commentDelete/{replyId}")
    public ResponseEntity<?> commentDelete(@PathVariable("replyId")Long replyId, @RequestBody Map<String, Long> body) {
        log.trace("commentDelete({}) invoked.", replyId);
        Long boardId = body.get("boardId");

        Integer deleted = this.boardService.deleteBoardReply(replyId);
        log.info("\t+ deleted: {}", deleted);

        // 리다이렉트할 URL을 JSON 객체로 반환
        return ResponseEntity.ok(Collections.singletonMap("redirectUrl", "/board/detailBoard/" + boardId));
    }

    // 게시글 신고 폼을 보여주는 메서드
    @GetMapping("/reportBoard/{id}")
    public ResponseEntity<?> reportBoardView(Model model, @PathVariable("id")Long id) {
        log.trace("reportBoardView({}) invoked.", id);

        try{
            BoardVO board = this.boardService.findBoard(id);

            List<ReportKategoriesVO> kategoriesList = this.boardService.findReportKategoriesList();

            Map<String, Object> resultMap = new LinkedHashMap<>();
            resultMap.put("kategoriesList", kategoriesList);
            resultMap.put("board", board);

            return ResponseEntity.ok().body(resultMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("내용을 불러오는데 실패했습니다.");
        }
    } // reportBoardView

    @PostMapping("/reportBoard/{boardId}")
    public String reportBoardWrite(String content, Long kategorieId, @PathVariable("boardId")Long boardId) {
        log.trace("reportBoardWrite({}, {}, {}) invoked.", content, kategorieId, boardId);
        // 현재 인증된 사용자의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증된 사용자의 이름(여기서는 사용자 ID로 사용)을 가져옵니다.
        String username = authentication.getName();
        // 사용자 이름(아이디)를 Long 타입으로 변환합니다.
        Long reporterId = Long.valueOf(username);

        Integer writinged = this.boardService.writingAReport(content, kategorieId, boardId, reporterId);
        log.info("\t+ writinged: {}", writinged);

        return "redirect:/board/detailBoard/" + boardId;
    } // reportReplyWrite

    @GetMapping("/reportReply/{id}")
    public ResponseEntity<?> reportReplyView(Model model, @PathVariable("id")Long id, Long boardId) {
        log.trace("reportReplyView({}) invoked." , id);

        try{
            BoardVO board = this.boardService.findBoard(boardId);
            BoardReplyVO reply = this.boardService.findBoardReply(id);

            List<ReportKategoriesVO> kategoriesList = this.boardService.findReportKategoriesList();

            Map<String, Object> resultMap = new LinkedHashMap<>();
            resultMap.put("kategoriesList", kategoriesList);
            resultMap.put("board", board);
            resultMap.put("reply", reply);

            return ResponseEntity.ok().body(resultMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("내용을 불러오는데 실패했습니다.");
        }
    } // reportReplyView

    @PostMapping("/reportReply/{replyId}")
    public String reportReplyWrite(String content, Long kategorieId, @PathVariable("replyId")Long replyId, Long boardId) {
        log.trace("reportReplyWrite({}, {}, {}) invoked.", content, kategorieId, replyId);
        // 현재 인증된 사용자의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증된 사용자의 이름(여기서는 사용자 ID로 사용)을 가져옵니다.
        String username = authentication.getName();
        // 사용자 이름(아이디)를 Long 타입으로 변환합니다.
        Long reporterId = Long.valueOf(username);

        Integer writinged = this.boardService.reportwriteAComment(content, kategorieId, replyId, reporterId);
        log.info("\t+ writinged: {}", writinged);

        return "redirect:/board/detailBoard/" + boardId;
    } // reportReplyWrite

} // end class
