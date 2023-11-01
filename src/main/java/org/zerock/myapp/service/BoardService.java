package org.zerock.myapp.service;

import org.apache.ibatis.annotations.Param;
import org.zerock.myapp.domain.BoardKategoriesVO;
import org.zerock.myapp.domain.BoardReplyVO;
import org.zerock.myapp.domain.BoardVO;

import java.util.List;

public interface BoardService {

    public abstract List<BoardVO> findBoardList();

    // 특정 게시글 조회
    public abstract BoardVO findBoard(Long id);

    // 게시글에 해당하는 댓글 목록 조회
    public abstract List<BoardReplyVO> findBoardReplyList(Long id);

    // 게시글에 해당하는 댓글 목록 조회
    public abstract BoardReplyVO findBoardReply(Long id);

    // 댓글 작성
    public abstract Integer insertReply(String content, Long memberId, Long id);

    // 댓글 수정
    public abstract Integer updateBoardReply(BoardReplyVO reply);

} // end interface
