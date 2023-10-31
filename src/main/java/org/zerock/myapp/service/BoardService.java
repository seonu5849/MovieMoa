package org.zerock.myapp.service;

import org.zerock.myapp.domain.BoardVO;

import java.util.List;

public interface BoardService {

    public abstract List<BoardVO> findBoardList();

} // end interface
