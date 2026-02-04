package com.schedule_management.controller;

import com.schedule_management.dto.ScheduleRequestDto;
import com.schedule_management.dto.ScheduleResponseDto;
import com.schedule_management.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")//공통경로
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> create(@RequestBody ScheduleRequestDto request) {
        ScheduleResponseDto result = scheduleService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public  ResponseEntity<List<ScheduleResponseDto>> getSchedules(@RequestParam(required = false) String author) {
        List<ScheduleResponseDto> schedules = scheduleService.getAll(author);
        return ResponseEntity.ok(schedules);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {

        ScheduleResponseDto schedule = scheduleService.getSelectedSchedule(id);
        return ResponseEntity.ok(schedule);
    }

    @PatchMapping("/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(id, requestDto);
    }



}
