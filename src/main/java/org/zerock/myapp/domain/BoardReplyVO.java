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

//    멤버테이블에서 leftjoin
    private String nickname;


} // end class
