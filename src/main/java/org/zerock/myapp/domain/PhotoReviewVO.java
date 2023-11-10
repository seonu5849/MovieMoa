package org.zerock.myapp.domain;

import lombok.Data;
import lombok.Value;

import java.sql.Timestamp;

@Value
public class PhotoReviewVO {

    private Long id;
    private Long productId;
    private Long memberId;
    private String content;
    private String photoPath;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private String memberName;

}
