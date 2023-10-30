package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class SearchHistoryVO {
    private Long memberId;
    private Long movieId;
    private Timestamp searchDate;
} // end class
