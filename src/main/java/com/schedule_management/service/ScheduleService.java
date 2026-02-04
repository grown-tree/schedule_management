package com.schedule_management.service;

import com.schedule_management.dto.ScheduleRequestDto;
import com.schedule_management.dto.ScheduleResponseDto;
import com.schedule_management.entity.Schedule;
import com.schedule_management.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

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
}
