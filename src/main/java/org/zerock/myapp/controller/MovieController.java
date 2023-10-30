package org.zerock.myapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zerock.myapp.domain.*;
import org.zerock.myapp.service.MovieJsonService;
import org.zerock.myapp.service.MovieService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor

@RequestMapping("/movie/*")
@Controller
public class MovieController {

    // http://localhost:8080/movie/movies

    private final MovieService movieService;
    private final MovieJsonService movieJsonService;

    // 영화 데이터 가져오기
    @GetMapping("/movieData")
    public String moviesDataView(Model model) {
        log.trace("moviesDataView() invoked.");

        return "movie/movieData";
    } // moviesData

    // 영화 데이터 가져오기
    @PutMapping("/movieData")
    public String moviesData(Model model, Long startNum, Long endNum) throws IOException {
        log.trace("moviesData({}, {}) invoked.", startNum, endNum);

        movieJsonService.jsonToGenres();
        movieJsonService.jsonToMovies(startNum, endNum);
        movieJsonService.jsonToMovieCredits(startNum, endNum);
        movieJsonService.jsonToCertification(startNum, endNum);

        model.addAttribute("updateSuccess", true);

        return "movie/movieData";
    } // moviesData

    // 영화 전체 목록
    @GetMapping("/movies")
    public String movieView(Model model) {
        log.trace("movieView() invoked.");


        List<MovieVO> allMovies = movieService.findAllMovies();
        allMovies.forEach(log::info);

        model.addAttribute("moviesList", allMovies);

        return "movie/movies";
    } // movieView

    // 영화 텍스트 검색 ( 제목, 배우, 스태프 )
    @GetMapping("/searchMovies")
    public String searchMovies(Model model, @RequestParam String searchInput, @RequestParam String searchCategory) {
        log.trace("searchMovies({}, {}) invoked.", searchInput, searchCategory);

        List<MovieVO> searchedMovies = movieService.searchMovies(searchInput, searchCategory);
        searchedMovies.forEach(log::info);

        model.addAttribute("moviesList", searchedMovies);
        return "movie/movies";
    }

    // 영화 장르검색
    @GetMapping("/searchByGenre")
    public String searchByGenre(@RequestParam List<String> searchGenre, Model model) {
        log.trace("searchByGenre({}) invoked.", searchGenre);

        List<MovieVO> searchedMovies = movieService.searchByGenre(searchGenre);
        searchedMovies.forEach(log::info);

        model.addAttribute("moviesList", searchedMovies);
        return "movie/movies";
    } // searchByGenre

    // 영화 날짜검색
    @GetMapping("/searchByDate")
    public String searchByDate(Model model, @RequestParam String startDate, @RequestParam String endDate) {
        log.trace("searchByDate({}, {}) invoked.", startDate, endDate);

        List<MovieVO> searchedMovies = movieService.searchByDate(startDate, endDate);
        searchedMovies.forEach(log::info);

        model.addAttribute("moviesList", searchedMovies);
        return "movie/movies";
    } // searchByDate

    // 영화 상세페이지
    @GetMapping("/movieDetail")
    public String movieDetail(Model model, @RequestParam Long movieId) {
        log.trace("movieDetail({}) invoked.", movieId);

        MovieVO movie = movieService.findDetailMovie(movieId);
        List<MovieGenreVO> genres = movieService.genreOfMovies(movieId);
        List<CreditsVO> casts = movieService.castsOfMovies(movieId);
        List<CreditsVO> crews = movieService.crewsOfMovies(movieId);
        CertificationVO certification = movieService.certificationsOfMovies(movieId);
        List<authorOfBoardVO> boards = movieService.findBoardByMovie(movieId);

        model.addAttribute("movie", movie);
        model.addAttribute("genres", genres);
        model.addAttribute("casts", casts);
        model.addAttribute("crews", crews);
        model.addAttribute("certification", certification);
        model.addAttribute("boards", boards);

        log.info("movie: {}", movie);
        log.info("certification: {}", certification);
        genres.forEach(log::info);
        casts.forEach(log::info);
        crews.forEach(log::info);
        boards.forEach(log::info);

        return "/movie/movieDetail";
    } // movieDetailView

} // end class
