package com.schedule_management.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String title;
    private String content;
    private String author;
    private String password;
    // 작성/수정일은 서버에서 관리하므로 DTO에는 포함x
}
