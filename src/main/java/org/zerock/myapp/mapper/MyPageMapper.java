package org.zerock.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.myapp.domain.BoardVO;

import java.util.List;

@Mapper
public interface MyPageMapper{
    public abstract List<BoardVO> findMyBoardList(@Param("MEMBER_ID") Long memberId,
                                                  @Param("offset") Integer offset,
                                                  @Param("perPage") Integer perPage);
    public abstract Integer totalMyBoardCount(@Param("memberId") Long memberId);



} //MyPageMapper
