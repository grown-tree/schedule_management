package com.schedule_management.repository;

import com.schedule_management.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 작성자명 기준 조회 (선택적 조건 포함) 및 수정일 기준 내림차순 정렬
    List<Schedule> findByAuthor(String author);
    // 작성자명 조건이 없는 경우 전체 조회 및 수정일 기준 내림차순 정렬
    List<Schedule> findAll();
    //수정일 기준 내림차순 정렬
    List<Schedule> findAllByOrderByUpdatedDateDesc();
}
