package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class ReportBoardsVO {
    private Long id;
    private String content;
    private String menu;
    private Timestamp reportDate;
    private Long boardId;
    private Long reporterId;
    private String title;
    private String name;
    private String status;
    private Timestamp suspensionPeriod;
    private Long boardWriterId;
    private String boardWriter;
} // end class
