package org.zerock.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.myapp.domain.BoardVO;

import java.util.List;


@Mapper
public interface BoardMapper {

    // 이벤트 목록 조회: 페이지네이션과 진행 여부에 따른 이벤트 목록 조회
    public abstract List<BoardVO> findBoardList();

} // end interface
