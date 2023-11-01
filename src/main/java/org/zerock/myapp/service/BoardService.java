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
    public abstract BoardReplyVO findBoardReplyList(Long id);

} // end interface
