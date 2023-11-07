package org.zerock.myapp.service;

import org.zerock.myapp.domain.BoardReplyVO;
import org.zerock.myapp.domain.BoardVO;

import java.util.List;

public interface MyPageService {

    public abstract Integer offset(Integer pageNum);
    
    // 회원 ID를 이용한 게시글 전체 조회
    public abstract List<BoardVO> findMyPageBoardList(Long memberId, Integer pageNum);

    // 페이징을 위한 특정 멤버의 게시글 총 레코드 수 조회
    public abstract Integer totalMyBoardByBoardCount(Long memberId);

    // 회원 ID를 이용한 리플 전체 조회
    public abstract List<BoardReplyVO> findMyPageReplyList(Long memberId, Integer pageNum);

    public abstract Integer totalMyReplyCount(Long memberId);

}
