package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class ReportBoardResponseVO {
    private Long reportBoardId;
    private String content;
    private Timestamp responseDate;
    private Long adminId;
} // end class
