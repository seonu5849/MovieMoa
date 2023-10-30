package org.zerock.myapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.myapp.domain.json.*;
import org.zerock.myapp.mapper.MovieMapper;
import org.zerock.myapp.service.JsonToObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2


@SpringBootTest
class JacksonTempTests {

    @Setter(onMethod_ = @Autowired)
    private MovieMapper movieMapper;

    @Setter(onMethod_ = @Autowired)
    private JsonToObject jsonToObject;

    // 범위 지정 시 시작할 영화 번호, 끝 영화 번호
    private Long startMovieNum = 620L;
    private Long endMovieNum = 680L;

    // 1개의 영화 데이터를 위한 영화 번호
    private Long MovieNum = 671L;

    @Test
    @Order(1)
    @DisplayName("모든 장르 출력 후 저장")
    void testGenreAll() throws IOException {

        GenreAll genreAll = this.jsonToObject.jsonToGenres();
        log.info("\t+ genreAll : {}", genreAll);

        // DB 넣기
        List<GenreInfo> genres = genreAll.getGenres();

        Integer affectedRows = null;

        for (GenreInfo genre : genres){
            affectedRows = this.movieMapper.insertGenre(genre);
        }

        log.info("\t+ affectedRows : {}", affectedRows);

    }

    @Test
    @Order(2)
    @DisplayName("해리포터로 검색했을때 영화 데이터 출력 후 저장")
    void testMovieInfo() throws IOException {

//        MovieInfo movieInfo = this.jsonToEntity.movieInfo(MovieNum); // 한개의 영화 데이터만 넣기
        List<Movies> movies = this.jsonToObject.jsonToMovies(startMovieNum, endMovieNum);// 여러 개의 영화 데이터를 넣기
        movies.forEach(log::info);

        // DB 저장
        for(Movies movie : movies) {
            Integer affectedRows = null;
            affectedRows = movieMapper.insertMovie(movie); // 영화 데이터 넣기
            log.info("\t+ affectedRows : {}", affectedRows);


            for (GenreInfo genre : movie.getGenres()) { // 영화별 장르 넣기
                affectedRows = this.movieMapper.insertMovieOfGenre(movie.getId(), genre.getId());
            }
            log.info("\t+ affectedRows : {}", affectedRows);

        }
    }

    @Test
    @Order(3)
    @DisplayName("영화 별 관람등급 출력 및 DB 저장")
    void testCertification() throws JsonProcessingException {

        // 영화 관람등급 정보 가져오기
        List<ReleaseDates> movieCertification = this.jsonToObject.jsonToCertification(startMovieNum, endMovieNum);

        // 모든 영화의 관람등급 정보를 출력하고 DB에 저장
        movieCertification.forEach(movie -> {
            List<CertificationResults> koreanReleaseDates = movie.getResults().stream()
                    .filter(releaseDate -> "KR".equals(releaseDate.getIso_3166_1()))
                    .collect(Collectors.toList());

            koreanReleaseDates.forEach(releaseDate -> {
                String isoCode = releaseDate.getIso_3166_1();
                String certification = releaseDate.getRelease_dates().get(0).getCertification(); // 중복 로직 제거

                // 정보를 출력
                log.info("\t+ id : {}", movie.getMovieId());
                log.info("\t+ iso_3166_1 = " + isoCode);
                log.info("\t+ certification = " + certification);

                // DB 저장 부분
                Integer affectedRows = movieMapper.insertCertification(movie.getMovieId(), isoCode, certification);
                log.info("\t+ affectedRows for certification: {}", affectedRows);
            });
        });
    } // testCertification

    @Test
    @Order(4)
    @DisplayName("영화 제작 참여자 정보 출력 및 DB 저장")
    void testMovieCredits() throws IOException {
        // 선택적으로 동작할 수 있도록 flag 설정
        boolean useSingleId = false;  // true일 경우 단일 ID 사용(1개 영화의 제작진 정보), false일 경우 범위 사용(여러 영화의 제작진 정보)

        List<Credits> movieCreditsList;

        if (useSingleId) {
            Credits singleMovieCredits = this.jsonToObject.jsonToMovieCredits(MovieNum);
            movieCreditsList = Collections.singletonList(singleMovieCredits);
        } else {
            movieCreditsList = jsonToObject.jsonToMovieCredits(startMovieNum, endMovieNum);
        }

        for (Credits movieCredits : movieCreditsList) {

            log.info("\t+ movieCredits : {}", movieCredits);

            List<Cast> top5Cast = movieCredits.getCast().subList(0, Math.min(5, movieCredits.getCast().size()));

            List<Crew> relevantCrew = movieCredits.getCrew().stream()
                    .filter(crew -> ("Directing".equals(crew.getDepartment()) && "Director".equals(crew.getJob()))
                            || "Writing".equals(crew.getDepartment()))
                    .collect(Collectors.toList());

            log.info("\t+ Top 5 Cast : {}", top5Cast);
            log.info("\t+ Directing or Writing Crew : {}", relevantCrew);

            // DB에 저장하는 부분 추가
            for (Cast cast : top5Cast) {
                movieMapper.insertCast(movieCredits.getMovieId(), cast);
            }
            for (Crew crew : relevantCrew) {
                movieMapper.insertCrew(movieCredits.getMovieId(), crew);
            }
        }
    } // testMovieCredits

} // end class