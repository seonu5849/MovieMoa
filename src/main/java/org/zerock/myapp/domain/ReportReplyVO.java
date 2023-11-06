package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class ReportReplyVO {
    private Long id;
    private String reportContent;
    private Timestamp reportDate;
    private Long reporterId; // 신고자 회원번호
    private Long replyId; // 신고 댓글번호
    private Long menu;
    private String complete;
    private String result;
    private String content;
    private String reporterName; // 신고자 이름
    private String status;
    private Timestamp suspensionPeriod;
    private Long replyWriterId; // 신고댓글 작성자 회원번호
    private String replyWriter; // 신고댓글 작성자 이름
    private String categoryName;
} // end class
