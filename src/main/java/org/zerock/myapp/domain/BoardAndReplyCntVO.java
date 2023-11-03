package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class BoardAndReplyCntVO {
    // Board 테이블 필드
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

    // Movies 테이블 필드
    private String movieTitle;

    // board_kategories 테이블 필드
    private String kategorieName;

    // Member 테이블 필드
    private String memberNickname;

    // Board_reply 테이블 카운트
    private Long replyCount;
} // end class
