package org.example.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.example.schedule.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.schedule.service.ScheduleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    @Autowired
    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleSaveResponse> saveSchedule(
            @RequestBody ScheduleSaveRequest request
    ){
        return ResponseEntity.ok(scheduleService.saveSchedule(request));
    }

    //전체 일정 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleGetAllResponse>> getSchedules(
            @RequestParam(required = false) String author //작성자명 포함 필수 아님
    ){
        return ResponseEntity.ok(scheduleService.findAll(author));
    }

    //단건 일정  조회
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleGetResponse> getSchedule(
            @PathVariable Long scheduleId //null가능
    ){
        return ResponseEntity.ok(scheduleService.findOne(scheduleId));
    }

    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResponse> updateSchedule(
            @PathVariable Long scheduleId, //null가능
            @RequestBody ScheduleUpdateRequest request
    ){
        return ResponseEntity.ok(scheduleService.update(scheduleId, request));
    }

    @DeleteMapping("/schedules/{scheduleId}")
    //@PostMapping("/schedules/{scheduleId}/delete") 예전방식
    public void deleteSchedule(
            @PathVariable Long scheduleId,
            @RequestParam String password //post로 보안 증가 또는 @RequestBody
    ){
        scheduleService.deleteOne(scheduleId, password);
    }
}
