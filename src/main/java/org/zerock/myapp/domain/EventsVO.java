package org.zerock.myapp.domain;

import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

@Value
public class EventsVO {
    private Long id;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String thumbnailPath;
    private String contentsPath;
    private Long adminId;
    private String nickname;
} // end class
