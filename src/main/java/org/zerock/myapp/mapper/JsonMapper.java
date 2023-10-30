package org.zerock.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zerock.myapp.domain.json.Cast;
import org.zerock.myapp.domain.json.Crew;
import org.zerock.myapp.domain.json.GenreInfo;
import org.zerock.myapp.domain.json.Movies;

@Mapper
public interface JsonMapper {

    // 영화 데이터 저장
    public abstract Integer insertMovie(Movies movies);

    // 장르 데이터 저장
    public abstract Integer insertGenre(GenreInfo genre);

    // 영화별 장르 데이터 저장
    public abstract Integer insertMovieOfGenre(Long movieId, Integer id);

    // 관람 등급 정보 저장
    public abstract Integer insertCertification(@Param("movieId") Long movieId,
                                                @Param("isoCode") String isoCode,
                                                @Param("certification") String certification);

    // 캐스트 정보 저장
    public abstract Integer insertCast(@Param("movieId") Long movieId, @Param("cast") Cast cast);

    // 크루 정보 저장
    public abstract Integer insertCrew(@Param("movieId") Long movieId, @Param("crew") Crew crew);

} // end interface