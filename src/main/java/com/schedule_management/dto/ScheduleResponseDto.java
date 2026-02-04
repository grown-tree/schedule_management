package com.schedule_management.dto;

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

    public ScheduleResponseDto(Long id, String title, String content, String author, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    @Getter
    public class GetSchedulsResponse {

        private final Long id;
        private final String title;
        private final String content;
        private final String author;
        private final LocalDateTime createdDate;
        private final LocalDateTime modifiedDate;

        public GetSchedulsResponse(Long id, String title, String content, String author, LocalDateTime createdDate, LocalDateTime modifiedDate) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.author = author;
            this.createdDate = createdDate;
            this.modifiedDate = modifiedDate;
        }

    }
}
