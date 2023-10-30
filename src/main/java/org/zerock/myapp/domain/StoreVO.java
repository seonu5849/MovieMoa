package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class StoreVO {
    private Long id;
    private Long adminId;
    private String title;
    private String content;
    private String price;
    private String usageLocation;
    private String posterPath;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long kategorieId;
} // end class
