package org.zerock.myapp.domain;

import lombok.Data;
import lombok.Value;

import java.sql.Timestamp;

@Data
public class SearchHistoryDTO {
    private Long memberId;
    private Long movieId;
    private Timestamp searchDate;


} // end class
