package com.schedule_management.service;

import com.schedule_management.dto.ScheduleRequestDto;
import com.schedule_management.dto.ScheduleResponseDto;
import com.schedule_management.entity.Schedule;
import com.schedule_management.repository.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    //일정 생성
    @Transactional
    public ScheduleResponseDto save(ScheduleRequestDto scheduleRequestDto){
        Schedule schedule =  new Schedule(
                scheduleRequestDto.getTitle(),
                scheduleRequestDto.getContent(),
                scheduleRequestDto.getAuthor(),
                scheduleRequestDto.getPassword()
        );
        Schedule saveSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(
                saveSchedule.getId(),
                saveSchedule.getTitle(),
                saveSchedule.getContent(),
                saveSchedule.getAuthor(),
                saveSchedule.getCreatedDate(),
                saveSchedule.getUpdatedDate()
        );
    }

    //일정 조회 : 전체조회, 작성자로 조회 (기본값 수정일 기준 내림차순 정렬)
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getAll(String author){
        List<Schedule> scheduleList;

        if (author != null && !author.isEmpty()) {
            // 작성자명이 있을 경우, 해당 작성자의 일정만 조회
            scheduleList = scheduleRepository.findByAuthor(author);
        } else {
            //작성자명이 없을 경우, 모든 일정 조회
            scheduleList = scheduleRepository.findAll();
        }
        //수정일 기준 기본값 오름차순 반대 = 내림차순 정렬
        scheduleList.sort(Comparator.comparing(Schedule::getUpdatedDate).reversed());

        //DTO로 변환 하여 반환(비밀번호제외됨)
        return scheduleList.stream().map(schedule -> new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getAuthor(),
                schedule.getCreatedDate(),
                schedule.getUpdatedDate()
        )).collect(Collectors.toList());
    }

    //선택 일정 조회
    @Transactional(readOnly = true)
    public ScheduleResponseDto getSelectedSchedule(Long id) {
        // id에 해당하는 일정을 찾고, 없으면 예외 처리
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 일정이 없습니다. " + id));

        // DTO로 변환하여 반환
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getAuthor(),
                schedule.getCreatedDate(),
                schedule.getUpdatedDate()
        );
    }
}
