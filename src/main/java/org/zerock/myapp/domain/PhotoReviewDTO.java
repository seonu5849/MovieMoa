package org.zerock.myapp.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PhotoReviewDTO {

    private Long id;
    private Long productId;
    private Long memberId;
    private String content;
    private String photoPath;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private String memberNickname;
}
