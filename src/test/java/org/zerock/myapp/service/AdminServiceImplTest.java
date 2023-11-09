package org.zerock.myapp.service;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.myapp.domain.ReportBoardsVO;
import org.zerock.myapp.domain.ReportReplyVO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@NoArgsConstructor

@SpringBootTest
class AdminServiceImplTest {

    @Setter(onMethod_ = @Autowired)
    private AdminService adminService;

//    @Test
//    @DisplayName("뷰에서 선택한 처리에 따른 회원상태 변경")
//    void testEditMemberStatus(){
//        log.trace("testEditMemberStatus() invoked");
//
//        Long memberId = 2L;
//        String currentStatus = "1일추가";
//
//        Integer affectedRows = adminService.editMemberStatus(memberId, currentStatus);
//
//        Assertions.assertThat(affectedRows).isEqualTo(1);
//
//    }

    @Test
    @DisplayName("신고들어온 내용 확인")
    void testFindReportedBoards(){
        List<ReportBoardsVO> lists = this.adminService.findReportedBoards(1);
        lists.forEach(log::info);
    }

    @Test
    @DisplayName("신고 댓글 확인")
    void testReportReply(){

        List<ReportReplyVO> list = this.adminService.findReportedReply(1);

        log.info("\t start");
        list.forEach(log::info);

    }

//    @Test
//    @DisplayName("신고 게시글 결과를 부여한 상태에서 만약 한번 더 수정하려 할때")
//    void testModifyMemberAndReport(){
//
//        Integer affectedRows = this.adminService.modifyMemberAndReport(2L, 33L, "1일추가", "qwer12");
//        Assertions.assertThat(affectedRows).isEqualTo(1);
//
//    }

}