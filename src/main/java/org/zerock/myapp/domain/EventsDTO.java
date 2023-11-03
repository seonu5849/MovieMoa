package org.zerock.myapp.domain;

import lombok.Data;
import lombok.Value;

import java.sql.Timestamp;

@Data
public class EventsDTO {
    private Long id;
    private String title;
    private Timestamp startAt;
    private Timestamp endAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String thumbnailPath;
    private String contentsPath;
    private Long adminId;
    private String nickname;
} // end class
