package org.zerock.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.myapp.domain.*;
import org.zerock.myapp.domain.json.Cast;
import org.zerock.myapp.domain.json.Crew;
import org.zerock.myapp.domain.json.GenreInfo;
import org.zerock.myapp.domain.json.Movies;

import java.util.List;

@Mapper
public interface MovieMapper {

    // 영화 데이터 저장
    public abstract Integer insertMovie(Movies movies);

    // 장르 데이터 저장
    public abstract Integer insertGenre(GenreInfo genre);

    // 영화별 장르 데이터 저장
    public abstract Integer insertMovieOfGenre(Long movieId, Long id);

    // 관람 등급 정보 저장
    public abstract Integer insertCertification(@Param("movieId") Long movieId,
                                                @Param("isoCode") String isoCode,
                                                @Param("certification") String certification);

    // 캐스트 정보 저장
    public abstract Integer insertCast(@Param("movieId") Long movieId, @Param("cast") Cast cast);

    // 크루 정보 저장
    public abstract Integer insertCrew(@Param("movieId") Long movieId, @Param("crew") Crew crew);

    // DB 내 모든 영화 목록
    public abstract List<MovieVO> findAllMovies();

    // 제목, 감독명, 영화 제작 참여자 명으로 검색
    public abstract List<MovieVO> searchMovies(@Param("searchInput") String searchInput, @Param("searchCategory") String searchCategory);

    // 장르별 검색
    public abstract List<MovieVO> searchByGenre(@Param("genres") List<String> searchGenre);

    // 개봉일자 검색
    public abstract List<MovieVO> searchByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);

    // 특정 영화의 상세 정보
    public abstract MovieVO findDetailMovie(@Param("movieId") Long movieId);

    // 특정 영화 내 장르 조회
    public abstract List<MovieGenreVO> genreOfMovies(@Param("movieId") Long movieId);

    // 특정 영화 내 배우 조회
    public abstract List<CreditsVO> castsOfMovies(@Param("movieId") Long movieId);

    // 특정 영화 내 스태프 조회
    public abstract List<CreditsVO> crewsOfMovies(@Param("movieId") Long movieId);

    // 특정 영화 내 관람등급 조회
    public abstract CertificationVO certificationsOfMovies(@Param("movieId") Long movieId);

    // 특정 영화의 게시글 조회
    public abstract List<authorOfBoardVO> findBoardByMovie(@Param("movieId") Long movieId);

    // 전체 영화의 수를 반환
    public abstract Integer totalCount();

} // end interface