package org.zerock.myapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    // 영화 전체 목록
    @GetMapping("/movies")
    public String movieView(Model model) {
        log.trace("movieView() invoked.");

        List<MovieVO> allMovies = movieService.findAllMovies();
        allMovies.forEach(log::info);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = null;
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            log.info("\t+ 인증된 사용자");
            String username = authentication.getName();
            memberId = Long.valueOf(username);
        }

        // 여기서 위시리스트 체크 상태를 저장할 맵을 생성합니다.
        Map<Long, Boolean> wishlistStatus = new HashMap<>();

        if (memberId != null) {
            for (MovieVO movie : allMovies) {
                Long movieId = movie.getId(); // 여기서 영화 ID를 가져옵니다.
                boolean wishCheck = movieService.WishlistCheck(movieId, memberId);
                log.info("\t+ Movie ID: {}, wishCheck: {}", movieId, wishCheck);

                // 위시리스트 체크 결과를 맵에 저장합니다.
                wishlistStatus.put(movieId, wishCheck);
            }
        }

        // 모델에 영화 목록과 위시리스트 체크 상태를 추가합니다.
        model.addAttribute("moviesList", allMovies);
        model.addAttribute("wishlistStatus", wishlistStatus);

        return "movie/movies";
    } // movieView

    // 영화 텍스트 검색 ( 제목, 배우, 스태프 )
    @GetMapping("/searchMovies")
    public String searchMovies(Model model, @RequestParam String searchInput, @RequestParam String searchCategory) {
        log.trace("searchMovies({}, {}) invoked.", searchInput, searchCategory);

        List<MovieVO> searchedMovies = movieService.searchMovies(searchInput, searchCategory);
        searchedMovies.forEach(log::info);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = null;
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            log.info("\t+ 인증된 사용자");
            String username = authentication.getName();
            memberId = Long.valueOf(username);
        }

        // 여기서 위시리스트 체크 상태를 저장할 맵을 생성합니다.
        Map<Long, Boolean> wishlistStatus = new HashMap<>();

        if (memberId != null) {
            for (MovieVO movie : searchedMovies) {
                Long movieId = movie.getId(); // 여기서 영화 ID를 가져옵니다.
                boolean wishCheck = movieService.WishlistCheck(movieId, memberId);
                log.info("\t+ Movie ID: {}, wishCheck: {}", movieId, wishCheck);

                // 위시리스트 체크 결과를 맵에 저장합니다.
                wishlistStatus.put(movieId, wishCheck);
            }
        }

        // 모델에 영화 목록과 위시리스트 체크 상태를 추가합니다.
        model.addAttribute("wishlistStatus", wishlistStatus);
        model.addAttribute("moviesList", searchedMovies);

        return "movie/movies";
    }

    // 영화 장르검색
    @GetMapping("/searchByGenre")
    public String searchByGenre(@RequestParam List<String> searchGenre, Model model) {
        log.trace("searchByGenre({}) invoked.", searchGenre);

        List<MovieVO> searchedMovies = movieService.searchByGenre(searchGenre);
        searchedMovies.forEach(log::info);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = null;
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            log.info("\t+ 인증된 사용자");
            String username = authentication.getName();
            memberId = Long.valueOf(username);
        }

        // 여기서 위시리스트 체크 상태를 저장할 맵을 생성합니다.
        Map<Long, Boolean> wishlistStatus = new HashMap<>();

        if (memberId != null) {
            for (MovieVO movie : searchedMovies) {
                Long movieId = movie.getId(); // 여기서 영화 ID를 가져옵니다.
                boolean wishCheck = movieService.WishlistCheck(movieId, memberId);
                log.info("\t+ Movie ID: {}, wishCheck: {}", movieId, wishCheck);

                // 위시리스트 체크 결과를 맵에 저장합니다.
                wishlistStatus.put(movieId, wishCheck);
            }
        }

        // 모델에 영화 목록과 위시리스트 체크 상태를 추가합니다.
        model.addAttribute("wishlistStatus", wishlistStatus);
        model.addAttribute("moviesList", searchedMovies);
        return "movie/movies";
    } // searchByGenre

    // 영화 날짜검색
    @GetMapping("/searchByDate")
    public String searchByDate(Model model, @RequestParam String startDate, @RequestParam String endDate) {
        log.trace("searchByDate({}, {}) invoked.", startDate, endDate);

        List<MovieVO> searchedMovies = movieService.searchByDate(startDate, endDate);
        searchedMovies.forEach(log::info);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = null;
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            log.info("\t+ 인증된 사용자");
            String username = authentication.getName();
            memberId = Long.valueOf(username);
        }

        // 여기서 위시리스트 체크 상태를 저장할 맵을 생성합니다.
        Map<Long, Boolean> wishlistStatus = new HashMap<>();

        if (memberId != null) {
            for (MovieVO movie : searchedMovies) {
                Long movieId = movie.getId(); // 여기서 영화 ID를 가져옵니다.
                boolean wishCheck = movieService.WishlistCheck(movieId, memberId);
                log.info("\t+ Movie ID: {}, wishCheck: {}", movieId, wishCheck);

                // 위시리스트 체크 결과를 맵에 저장합니다.
                wishlistStatus.put(movieId, wishCheck);
            }
        }

        // 모델에 영화 목록과 위시리스트 체크 상태를 추가합니다.
        model.addAttribute("wishlistStatus", wishlistStatus);
        model.addAttribute("moviesList", searchedMovies);
        return "movie/movies";
    } // searchByDate

    // 영화 상세페이지
    @GetMapping("/movieDetail")
    public String movieDetail(Model model, @RequestParam Long movieId) {
        log.trace("movieDetail({}) invoked.", movieId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = null;
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            log.info("\t+ 인증된 사용자");
            String username = authentication.getName();
            memberId = Long.valueOf(username);
            Integer insertSearchHistory = this.movieService.insertSearchHistory(memberId, movieId);
            log.info("\t+ insertSearchHistory: {}", insertSearchHistory);
        }

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

    @PostMapping("/wishlist/{movieId}")
    public ResponseEntity<?> toggleWishlist(@PathVariable Long movieId) {
        log.trace("toggleWishlist({}) invoked.", movieId);
        // 현재 인증된 사용자의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증된 사용자의 이름(여기서는 사용자 ID로 사용)을 가져옵니다.
        String username = authentication.getName();
        // 사용자 이름(아이디)를 Long 타입으로 변환합니다.
        Long memberId = Long.valueOf(username);

        try {
            // wishlist 상태 체크 (wishlist 안누른 상태 = false)
            boolean wishCheck = movieService.WishlistCheck(movieId, memberId);
            log.info("\t+ wishCheck: {}", wishCheck);

            if (!wishCheck) {
                log.info("\t+ wishlist 누르지 않음, wishlist 추가");
                // wishlist 누르지 않았다면, wishlist 추가
                movieService.addWishlist(movieId, memberId);
            } else {
                log.info("\t+ wishlist 이미 누름, wishlist 취소");
                // wishlist 이미 눌렀다면, wishlist 취소
                movieService.cancelWishlist(movieId, memberId);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("wishlist", !wishCheck);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    } // toggleWishlist

} // end class
