package org.zerock.myapp.domain;

import lombok.Value;

import java.util.Date;

@Value
public class MovieVO {
    private Long id;
    private String originalTitle;
    private String title;
    private Date releaseDate;
    private Integer runtime;
    private String posterPath;
    private Double voteAverage;
    private Double popularity;
    private String backdropPath;
    private String overview;
    private String status;
    private String tagline;
    private String homepage;
} // end class
