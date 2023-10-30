package org.zerock.myapp.service;

import org.zerock.myapp.domain.json.Credits;
import org.zerock.myapp.domain.json.GenreAll;
import org.zerock.myapp.domain.json.Movies;
import org.zerock.myapp.domain.json.ReleaseDates;

import java.io.IOException;
import java.util.List;

public interface MovieJsonService {

    // 모든 장르 정보를 가져오는 메서드
    public abstract Integer jsonToGenres() throws IOException;

    // 주어진 범위의 영화 정보(여러개)를 가져오는 메서드
    public abstract Integer jsonToMovies(Long startNum, Long endNum);

    // 주어진 범위의 영화의 제작진 정보(여러개)를 가져오는 메서드
    public abstract Integer jsonToMovieCredits(Long startNum, Long endNum);

    // 주어진 범위의 영화의 관람 등급 정보(여러개)를 가져오는 메서드
    public abstract Integer jsonToCertification(Long startNum, Long endNum);

    // 주어진 영화 ID에 해당하는 영화 정보(1개)를 가져오는 메서드
    public abstract Movies jsonToMovie(Long movieId) throws IOException;

    // 주어진 영화 ID에 해당하는 영화의 제작진 정보를 가져오는 메서드
    public abstract Credits jsonToMovieCredit(Long movieId) throws IOException;

} // end interface