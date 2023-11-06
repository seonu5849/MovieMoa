package org.zerock.myapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.BoardVO;
import org.zerock.myapp.domain.MemberVO;
import org.zerock.myapp.mapper.MyPageMapper;

import java.util.List;

@Log4j2
@RequiredArgsConstructor

@Service
public class MyPageServiceImpl implements MyPageService{

    private final MyPageMapper myPageMapper;

    private int perPage = 10;

    @Override
    public Integer offset(Integer pageNum){ // 페이징 처리를 통해 해당되는 레코드를 출력
        return (pageNum - 1) * perPage;
    }

    @Override
    public List<BoardVO> findMyPageBoardList(Long MEMBER_ID, Integer pageNum) {
        log.trace("findMyPageBoardList({}, {}) invoked.", MEMBER_ID, pageNum);

        Integer offset = offset(pageNum);
        return this.myPageMapper.findMyBoardList(MEMBER_ID, offset, perPage);

    } //findMyPageBoardList

    @Override
    public Integer totalMemberByBoardCount(Long memberId) {
        log.trace("totalEventCount({}) invoked.", memberId);

        Integer totalPages = this.myPageMapper.totalMyBoardCount(memberId) / perPage;

        if(this.myPageMapper.totalMyBoardCount(memberId) % perPage != 0){
            totalPages++;
        }

        return totalPages;
    } //totalMemberByBoardCount

} //MyPageServiceImpl
