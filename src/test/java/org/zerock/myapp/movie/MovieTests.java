package org.zerock.myapp.movie;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.myapp.domain.BoardReplyVO;
import org.zerock.myapp.service.BoardServiceImpl;
import org.zerock.myapp.service.MemberServiceImpl;
import org.zerock.myapp.service.MovieServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2

@NoArgsConstructor

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@SpringBootTest
public class MovieTests {
    @Setter(onMethod_ = @Autowired)
    private MemberServiceImpl memberService;

    @Setter(onMethod_ = @Autowired)
    private MovieServiceImpl movieService;



    @Test
    @DisplayName("영화 검색 기록 저장")
    void InsertMovieHistory(){
        log.trace("InsertMovieHistory() invoked");

        Long memberId = 4L;
        Long movieId = 663L;

        Integer loaded = this.movieService.insertSearchMovieHistory(memberId, movieId);

        assertThat(1).isEqualTo(loaded);
        log.info("loaded({}) Row", loaded);

    }//InsertMovieHistory

    @Test
    @DisplayName("영화 검색 기록 삭제")
    void DeleteMovieHistory(){
        log.trace("DeleteMovieHistory() invoked");

        Long memberId = 4L;
        Long movieId = 647L;

//        Integer affteted = this.memberService.deleteMyHistory(memberId, movieId);

//        log.info("affteted({}) Row Deleted", affteted);

    }//DeleteMovieHistory

    @Test
    @DisplayName("영화 검색 기록 전체 삭제")
    void DeleteMovieHistoriesAll(){
        log.trace("DeleteMovieHistoriesAll() invoked");

        Long memberId = 4L;

        Integer affteted = this.memberService.deleteMyAllHistories(memberId);

        log.info("affteted({}) Row Deleted", affteted);

    }//DeleteMovieHistory

} //MyPageTests
