package org.zerock.myapp.domain.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Movies {

    private Long id; // 영화번호
    private String original_title; // 원제
    private String title; // 제목
    private String release_date; // 개봉일자
    private Integer runtime; // 상영시간
    @JsonProperty("poster_path")
    private String posterPath; // 포스터
    private Double vote_average; // IMAD 평점
    private Double popularity; // 인기도
    private String backdrop_path;
    private String overview; // 줄거리
    private String status; // 상영 상태  예: Released, Post Production
    private String tagline; // 간단 소개
    private String homepage; // 관련 홈페이지 URL

    private List<ReleaseDates> certification_id; // 관람등급
    private List<GenreInfo> genres; // 장르
    private List<Credits> credits; // 제작참여자

}