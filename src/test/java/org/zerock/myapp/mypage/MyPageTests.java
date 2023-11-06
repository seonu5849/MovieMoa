package org.zerock.myapp.mypage;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.myapp.domain.BoardVO;
import org.zerock.myapp.service.BoardServiceImpl;
import org.zerock.myapp.service.MyPageServiceImpl;

import java.util.List;

@Log4j2

@NoArgsConstructor

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@SpringBootTest
public class MyPageTests {
    @Setter(onMethod_ = @Autowired)
    private MyPageServiceImpl myPageService;

    @Setter(onMethod_ = @Autowired)
    private BoardServiceImpl boardService;

//    @Test
//    @DisplayName("DisplayMyBoard")
//    void DisplayMyBoard() {
//        log.trace("DisplayMyBoard() invoked");
//        Long MemberId = 4L;
//        List<BoardVO> findBoard = this.myPageService.findMyPageBoardList(MemberId,);
//
//        Assertions.assertThat(findBoard).isNotNull();
//        findBoard.forEach(log::info);
//
//    } //DisplayMyBoard

    @Test
    @DisplayName("게시글 만들기")
    void WritePost(){
        log.trace("WritePost");

        for(int i = 0; i<100; i++){
            this.boardService.postWriting(
                    "test title"+ i,
                    "대한민국의 국민이 되는 요건은 법률로 정한다. 헌법재판소는 법관의 자격을 가진 9인의 재판관으로 구성하며, 재판관은 대통령이 임명한다. 국회는 상호원조 또는 안전보장에 관한 조약, 중요한 국제조직에 관한 조약, 우호통상항해조약, 주권의 제약에 관한 조약, 강화조약, 국가나 국민에게 중대한 재정적 부담을 지우는 조약 또는 입법사항에 관한 조약의 체결·비준에 대한 동의권을 가진다"+ i,
                    1L,
                    null,
                    4L);
        }

    }//

} //MyPageTests
