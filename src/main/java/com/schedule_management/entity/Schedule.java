package com.schedule_management.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends WriteTime {

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

}
