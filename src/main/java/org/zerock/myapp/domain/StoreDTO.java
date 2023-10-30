package org.zerock.myapp.domain;

import lombok.Data;
import lombok.Value;

import java.sql.Timestamp;

@Data
public class StoreDTO {
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
    private String name;
} // end class
