package org.zerock.myapp.service;

import org.apache.ibatis.annotations.Param;
import org.zerock.myapp.domain.*;

import java.util.List;
import java.util.Map;

public interface BoardService {

    // 게시글 조회
    public abstract List<BoardAndReplyCntVO> findBoardList(Integer pageNum);

    // 게시글 검색에 따른 목록 조회
    public abstract List<BoardAndReplyCntVO> BoardSearchList(String category, String query, Integer pageNum);

    // 게시글 작성
    public abstract Integer postWriting(String title, String content, Long kategorieId, Long movieId, Long memberId);

    // 게시글 수정
    public abstract Integer updateBoard(Long id, String title, String content, Long kategorieId, Long movieId, Long memberId);

    // 게시글 삭제
    public abstract Integer deleteAPost(Long id);

    // 게시판 카테고리 조회
    public abstract List<BoardKategoriesVO> findBoardKategoriesList();

    // 신고 카테고리 조회
    public abstract List<ReportKategoriesVO> findReportKategoriesList();

    // 신고글 작성
    public abstract Integer writingAReport(String content, Long kategorieId, Long boardId, Long reporterId);

    // 신고댓글 작성
    public abstract Integer reportwriteAComment(String content, Long kategorieId, Long replyId, Long reporterId);

    // 영화 검색 목록 조회
    public abstract List<MovieVO> searchMovies(String searchInput);

    // 특정 게시글 조회
    public abstract BoardVO findBoard(Long id);

    // 특정 게시글 조회
    public abstract Integer viewCountUp(Long id);

    // 사용자가 해당 게시글에 좋아요를 눌렀는지 확인
    public abstract Boolean likescheck(Long boardId, Long memberId);

    // 이미 좋아요를 눌렀다면, 좋아요 취소
    public abstract Integer cancelLikes(Long boardId, Long memberId);

    // 이미 좋아요를 눌렀다면, 좋아요 취소
    public abstract Integer LikesCountDown(Long boardId);

    // 좋아요를 누르지 않았다면, 좋아요 추가
    public abstract Integer addLikes(Long boardId, Long memberId);

    // 좋아요를 누르지 않았다면, 좋아요 추가
    public abstract Integer LikesCountUp(Long boardId);

    // 좋아요 수 조회
    public abstract Integer getLikeCount(Long boardId);

    // 게시글에 해당하는 댓글 목록 조회
    public abstract List<BoardReplyVO> findBoardReplyList(Long id);

    // 특정 댓글  조회
    public abstract BoardReplyVO findBoardReply(Long id);

    // 댓글 작성
    public abstract Integer insertReply(String content, Long memberId, Long id);

    // 댓글 수정
    public abstract Integer updateBoardReply(Long id, String content);

    // 댓글 삭제
    public abstract Integer deleteBoardReply(Long replyId);

    // 페이징 처리를 위한 메서드
    public abstract Integer offset(Integer pageNum);

    // 게시판 수 조회
    public abstract Map<String, Integer> totalBoardListCnt();

    // 게시판 검색 된 게시글 수 조회
    public abstract Map<String, Integer> boardSearchListCnt(String category, String query);

} // end interface
