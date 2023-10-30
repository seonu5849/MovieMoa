package org.zerock.myapp.service;

import org.zerock.myapp.domain.*;

import java.util.List;

public interface MovieService {

    // 전체 영화 목록
    public abstract List<MovieVO> findAllMovies();

    // 제목, 스태프명, 배우명으로 검색
    public abstract List<MovieVO> searchMovies(String searchInput, String searchCategory);

    // 장르별 검색
    public abstract List<MovieVO> searchByGenre(List<String> searchGenre);

    // 개봉 일자 검색
    public abstract List<MovieVO> searchByDate(String startDate, String endDate);

    // 특정 영화의 상세 정보
    public abstract MovieVO findDetailMovie(Long memberId);

    // 특정 영화 내 장르 조회
    public abstract List<MovieGenreVO> genreOfMovies(Long memberId);

    // 특정 영화 내 배우 조회
    public abstract List<CreditsVO> castsOfMovies(Long memberId);

    // 특정 영화 내 스태프 조회
    public abstract List<CreditsVO> crewsOfMovies(Long memberId);

    // 특정 영화 내 관람등급 조회
    public abstract CertificationVO certificationsOfMovies(Long memberId);

    // 특정 영화의 게시글 조회
    public abstract List<authorOfBoardVO> findBoardByMovie(Long memberId);

} // end interface
