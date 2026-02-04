package com.schedule_management.dto;

import com.schedule_management.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public ScheduleResponseDto(Schedule entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.createdDate = entity.getCreatedDate();
        this.updatedDate = entity.getUpdatedDate();
    }
}
