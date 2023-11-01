package org.zerock.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.myapp.domain.BoardKategoriesVO;
import org.zerock.myapp.domain.BoardReplyVO;
import org.zerock.myapp.domain.BoardVO;

import java.util.List;


@Mapper
public interface BoardMapper {

    // 이벤트 목록 조회: 페이지네이션과 진행 여부에 따른 이벤트 목록 조회
    public abstract List<BoardVO> findBoardList();

    // 특정 게시글 조회
    public abstract BoardVO findBoard(@Param("id")Long id);

    // 게시글에 해당하는 댓글 목록 조회
    public abstract List<BoardReplyVO> findBoardReplyList(@Param("id")Long id);

    // 게시글에 해당하는 댓글 목록 조회
    public abstract BoardReplyVO findBoardReply(@Param("id")Long id);

    // 댓글 작성
    public abstract Integer insertReply(@Param("content")String content, @Param("memberId")Long memberId, @Param("id")Long id);

    // 댓글 수정
    public abstract Integer updateBoardReply(@Param("reply")BoardReplyVO reply);

} // end interface
