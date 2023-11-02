package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class LikesVO {
    private Long id;
    private Long boardId;
    private Long movieId;
    private Timestamp createdAt;
} // end class
