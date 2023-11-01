package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class BoardReplyVO {
    private Long id;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long memberId;
    private Long boardId;
    private String nickname;
} // end class
