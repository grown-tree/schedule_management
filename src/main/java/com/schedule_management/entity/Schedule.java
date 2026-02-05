package com.schedule_management.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(length = 30, nullable = false)
        private String title;

        @Column(length = 200)
        private String content;

        @Column(nullable = false)
        private String author;
        @Column(nullable = false)
        private String password; //응답 즉 DTO에서는 제외

        public Schedule(String title, String content, String author, String password) {
                this.title = title;
                this.content = content;
                this.author = author;
                this.password = password;
        }

        // 일정 수정 (제목, 작성자명만 수정 가능) 날짜는 자동업뎃
        public void update(String title, String author) {
                this.title = title;
                this.author = author;
        }

}
