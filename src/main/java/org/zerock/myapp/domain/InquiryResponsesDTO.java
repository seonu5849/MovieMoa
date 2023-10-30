package org.zerock.myapp.domain;

import lombok.Data;
import lombok.Value;

import java.sql.Timestamp;

@Data
public class InquiryResponsesDTO {
    private Long id;
    private String responsesContent;
    private Timestamp createdAt;
    private Long adminId;
} // end class
