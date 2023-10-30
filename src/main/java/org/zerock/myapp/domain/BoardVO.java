package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class BoardVO {
    private Long id;
    private String title;
    private String content;
    private Long favorites;
    private Long viewCount;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long kategorieId;
    private Long movieId;
    private Long memberId;
} // end class
