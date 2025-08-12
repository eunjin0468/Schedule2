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
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    //일정 저장
    @Transactional
    public ScheduleSaveResponse saveSchedule(String title, String content, Long userId) {
        //1. 유저 조회
        User user = userRepository.findByIdOrElseThrow(userId);

        //2. 일정 생성
        Schedule schedule = new Schedule(title, content);
        schedule.setUser(user); //연관관계 설정
        user.getSchedules().add(schedule); //양방향

        //3. 저장
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
    public ScheduleGetResponse findById(Long scheduleId){
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

        // 2. 유저 조회
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("해당 id의 유저가 없습니다.")
        );

        // 3. 비밀번호 검증
        if (!ObjectUtils.nullSafeEquals(user.getPassword(), request.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 연관관계 포함해서 일정 업데이트
        schedule.updateSchedule(request.getTitle(), request.getContent(),user);

        return new ScheduleUpdateResponse(
                schedule.getTitle(),
                schedule.getContent()
        );
    }



    //특정 일정 삭제
    public void delete(Long scheduleId){
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        scheduleRepository.delete(findSchedule);
    }
}