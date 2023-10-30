package org.zerock.myapp.domain;

import lombok.Value;

@Value
public class CreditsVO {
    private Long creditsId;
    private Long id;
    private String name;
    private String profilePath;
    private String character;
    private String department;
    private String job;
    private Long movieId;
} // end class
