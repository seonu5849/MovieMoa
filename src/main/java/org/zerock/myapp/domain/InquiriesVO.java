package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class InquiriesVO {
    private Long id;
    private String title;
    private String content;
    private Timestamp createdAt;
    private Long memberId;
} // end class
