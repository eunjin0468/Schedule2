package org.example.schedule.service;

import org.example.schedule.dto.*;
import lombok.RequiredArgsConstructor;
import org.example.schedule.entity.Schedule;
import org.example.schedule.entity.User;
import org.example.schedule.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.example.schedule.repository.ScheduleRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;


    //일정 저장
    @Transactional
    public ScheduleSaveResponse saveSchedule(String title, String content, Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId); // 유저 조회

        Schedule schedule = new Schedule(title, content);
        schedule.setUser(user); // 유저 설정
        user.getSchedules().add(schedule); // 양방향 연관관계

        Schedule saved = scheduleRepository.save(schedule);

        return new ScheduleSaveResponse(
                saved.getScheduleId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getCreatedAt(),
                saved.getModifiedAt()
        );
    }

    //전체 일정 조회
    @Transactional(readOnly = true)
    public List<ScheduleGetAllResponse> findAll() {
        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleGetAllResponse::toDto)
                .toList();
    }

    //특정 일정 조회
    public ScheduleGetResponse findById(Long scheduleId) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        User user = findSchedule.getUser();


        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 스케줄이 없습니다"));


        return new ScheduleGetResponse(findSchedule.getScheduleId(), findSchedule.getUser().getUserName(), findSchedule.getTitle(), findSchedule.getContent(), findSchedule.getCreatedAt(), findSchedule.getModifiedAt());
    }

    //일정 수정
    @Transactional
    public ScheduleUpdateResponse updateSchedule(Long scheduleId, ScheduleUpdateRequest request) {
        // 1. 일정 조회
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("해당 id의 일정이 없습니다.")
        );

        // 연관관계 포함해서 일정 업데이트
        schedule.updateSchedule(request.getTitle(), request.getContent(), schedule.getUser());

        return new ScheduleUpdateResponse(
                schedule.getTitle(),
                schedule.getContent()
        );
    }

    public Optional<Schedule> findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId);
    }

    // 특정 일정 삭제
    @Transactional
    public void delete(Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        schedule.getUser().getSchedules().remove(schedule);

        scheduleRepository.delete(schedule);

    }
}