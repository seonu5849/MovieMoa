package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class ReportReplyVO {
    private Long id;
    private String content;
    private Timestamp reportDate;
    private Long reporterId;
    private Long replyId;
    private String menu;
} // end class
