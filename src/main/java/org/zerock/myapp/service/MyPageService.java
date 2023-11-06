package org.zerock.myapp.service;

import org.zerock.myapp.domain.BoardVO;
import org.zerock.myapp.domain.MemberVO;

import java.util.List;

public interface MyPageService {

    public abstract Integer offset(Integer pageNum);
    
    // 회원 ID를 이용한 게시글 전체 조회
    public abstract List<BoardVO> findMyPageBoardList(Long memberId, Integer pageNum);

    // 페이징을 위한 특정 멤버의 게시글 총 레코드 수 조회
    public abstract Integer totalMemberByBoardCount(Long memberId);
    
}
