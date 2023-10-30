package org.zerock.myapp.domain;

import lombok.Value;

import java.sql.Timestamp;

@Value
public class BoardFilesVO {
    private Long id;
    private String name;
    private String filePath;
    private Timestamp createdAt;
    private Long boardId;
} // end class
