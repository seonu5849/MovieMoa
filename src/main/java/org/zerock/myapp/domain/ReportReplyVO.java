package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class ReportReplyVO {
    private Long id;
    private String reportContent;
    private Timestamp reportDate;
    private Long reporterId;
    private Long replyId;
    private Long menu;
    private String content;
    private String reporterName;
    private String status;
    private Timestamp suspensionPeriod;
    private String boardWriter;
    private String categoryName;
} // end class
