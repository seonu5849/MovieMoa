package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class InquiryResponsesVO {
    private Long id;
    private String responsesContent;
    private Timestamp createdAt;
    private Long adminId;
} // end class
