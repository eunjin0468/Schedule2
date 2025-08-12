package org.example.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.example.schedule.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.schedule.service.ScheduleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    @Autowired
    private final ScheduleService scheduleService;

    //일정 등록
    @PostMapping("/schedules")
    public ResponseEntity<ScheduleSaveResponse> saveSchedule(
            @RequestBody ScheduleSaveRequest request
    ){
        ScheduleSaveResponse response =
                scheduleService.saveSchedule(
                        request.getTitle(),
                        request.getContent(),
                        request.getUserId()
                );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //전체 일정 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleGetAllResponse>> getSchedules(){
        List<ScheduleGetAllResponse> schedules = scheduleService.findAll();
        if (schedules.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }


    //특정 유저 일정 조회
    @GetMapping("/schedules/{userId}")
    public ResponseEntity<ScheduleGetResponse> findById(
         @PathVariable Long userId
    ){
        ScheduleGetResponse scheduleGetResponseDto = scheduleService.findById(userId);
        return new ResponseEntity<>(scheduleGetResponseDto, HttpStatus.OK);
    }

    //일정 수정
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleUpdateRequest request
    ){
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleId, request));
    }

    //특정 일정 삭제
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Object> delete(@PathVariable Long scheduleId){
        scheduleService.delete(scheduleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    //전체 일정 조회
//    @GetMapping("/schedules")
//    public ResponseEntity<List<ScheduleGetAllResponse>> getSchedules(
//            @RequestParam(required = false) String author //작성자명 포함 필수 아님
//    ){
//        return ResponseEntity.ok(scheduleService.findAll(author));
//    }

//    //단건 일정 조회
//    @GetMapping("/schedules/{scheduleId}")
//    public ResponseEntity<ScheduleGetResponse> getSchedule(
//            @PathVariable Long scheduleId //null가능
//    ){
//        return ResponseEntity.ok(scheduleService.findOne(scheduleId));
//    }
//
//    //일정 수정
//    @PutMapping("/schedules/{scheduleId}")
//    public ResponseEntity<ScheduleUpdateResponse> updateSchedule(
//            @PathVariable Long scheduleId,
//            @RequestBody ScheduleUpdateRequest request
//    ){
//        return ResponseEntity.ok(scheduleService.update(scheduleId, request));
//    }
//
//    //일정 삭제
//    @DeleteMapping("/schedules/{scheduleId}")
//    public void deleteSchedule(
//            @PathVariable Long scheduleId,
//            @RequestParam String password //post로 보안 증가 또는 @RequestBody
//    ){
//        scheduleService.deleteOne(scheduleId, password);
//    }
}
