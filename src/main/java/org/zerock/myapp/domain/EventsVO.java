package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class EventsVO {
    private Long id;
    private String title;
    private Timestamp startAt;
    private Timestamp endAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String thumbnailPath;
    private String contentsPath;
    private Long adminId;
} // end class
