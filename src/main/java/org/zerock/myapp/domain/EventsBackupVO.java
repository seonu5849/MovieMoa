package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class EventsBackupVO {
    private Long id;
    private String title;
    private String eventPath;
    private Timestamp startAt;
    private Timestamp endAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long memberId;
} // end class
