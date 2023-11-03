package org.zerock.myapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.*;
import org.zerock.myapp.mapper.BoardMapper;

import java.util.List;

@Log4j2
@RequiredArgsConstructor

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Override
    public List<BoardAndReplyCntVO> findBoardList() {
        return boardMapper.findBoardList();
    }

    @Override
    public Integer postWriting(String title, String content, Long kategorieId, Long movieId, Long memberId) {
        return boardMapper.postWriting(title, content, kategorieId, movieId, memberId);
    }

    @Override
    public Integer updateBoard(Long id, String title, String content, Long kategorieId, Long movieId, Long memberId) {
        return boardMapper.updateBoard(id, title, content, kategorieId, movieId, memberId);
    }

    @Override
    public Integer deleteAPost(Long id) {
        return boardMapper.deleteAPost(id);
    }

    @Override
    public List<BoardKategoriesVO> findBoardKategoriesList() {
        return boardMapper.findBoardKategoriesList();
    }

    @Override
    public List<ReportKategoriesVO> findReportKategoriesList() {
        return boardMapper.findReportKategoriesList();
    }

    @Override
    public Integer writingAReport(String content, Long kategorieId, Long boardId, Long reporterId) {
        return boardMapper.writingAReport(content, kategorieId, boardId, reporterId);
    }

    @Override
    public Integer reportwriteAComment(String content, Long kategorieId, Long replyId, Long reporterId) {
        return boardMapper.reportwriteAComment(content, kategorieId, replyId, reporterId);
    }

    @Override
    public List<MovieVO> searchMovies(String searchInput) {
        return boardMapper.searchMovies(searchInput);
    }

    @Override
    public BoardVO findBoard(Long id) {
        return boardMapper.findBoard(id);
    }

    @Override
    public Integer viewCountUp(Long id) {
        return boardMapper.viewCountUp(id);
    }

    @Override
    public Boolean likescheck(Long boardId, Long memberId) {
        Integer likescheck = boardMapper.likescheck(boardId, memberId);
        
        if(likescheck == 0){
            // 좋아요 누르지 않음
            return false;
        } else {
            // 좋아요 이미 누름
            return true;
        }
    }

    @Override
    public Integer cancelLikes(Long boardId, Long memberId) {
        return boardMapper.cancelLikes(boardId, memberId);
    }

    @Override
    public Integer LikesCountDown(Long boardId) {
        return boardMapper.LikesCountDown(boardId);
    }

    @Override
    public Integer addLikes(Long boardId, Long memberId) {
        return boardMapper.addLikes(boardId, memberId);
    }

    @Override
    public Integer LikesCountUp(Long boardId) {
        return boardMapper.LikesCountUp(boardId);
    }

    @Override
    public Integer getLikeCount(Long boardId) {
        return boardMapper.getLikeCount(boardId);
    }

    @Override
    public List<BoardReplyVO> findBoardReplyList(Long id) {
        return boardMapper.findBoardReplyList(id);
    }

    @Override
    public BoardReplyVO findBoardReply(Long id) {
        return boardMapper.findBoardReply(id);
    }

    @Override
    public Integer insertReply(String content, Long memberId, Long id) {
        return boardMapper.insertReply(content, memberId, id);
    }

    @Override
    public Integer updateBoardReply(Long id, String content) {
        return boardMapper.updateBoardReply(id, content);
    }

    @Override
    public Integer deleteBoardReply(Long replyId) {
        return boardMapper.deleteBoardReply(replyId);
    }

} // end class
