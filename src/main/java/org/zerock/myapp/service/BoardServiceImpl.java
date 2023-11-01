package org.zerock.myapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.BoardKategoriesVO;
import org.zerock.myapp.domain.BoardReplyVO;
import org.zerock.myapp.domain.BoardVO;
import org.zerock.myapp.mapper.BoardMapper;

import java.util.List;

@Log4j2
@RequiredArgsConstructor

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Override
    public List<BoardVO> findBoardList() {
        return boardMapper.findBoardList();
    }

    @Override
    public BoardVO findBoard(Long id) {
        return boardMapper.findBoard(id);
    }

    @Override
    public BoardReplyVO findBoardReplyList(Long id) {
        return boardMapper.findBoardReplyList(id);
    }

} // end class
