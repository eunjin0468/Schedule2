package org.example.schedule.service;

import org.example.schedule.dto.*;
import org.example.schedule.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.example.schedule.repository.ScheduleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    //일정 생성
    @Transactional
    public ScheduleSaveResponse saveSchedule(ScheduleSaveRequest request) {
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                request.getAuthor(),
                request.getPassword()
        );

        //일정 객체를 저장소에 저장하고, 응답 DTO에 반환
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new  ScheduleSaveResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getAuthor(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );

    }

    //전체 일정 조회
    @Transactional(readOnly = true)
    public List<ScheduleGetAllResponse> findAll(String author){
        List<Schedule> schedules;

        if(author == null){
            schedules = scheduleRepository.findAll();
        }else{
            schedules = scheduleRepository.findByAuthor(author);
        }

        return schedules.stream()
                .map(schedule -> new ScheduleGetAllResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getAuthor(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                ))
                .collect(Collectors.toList());
    }

    //단건 일정 조회
    @Transactional(readOnly = true)
    public ScheduleGetResponse findOne(long scheduleId) {

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow( //null인 id 처리
                () -> new IllegalArgumentException(("해당 id의 일정이 없습니다."))
        );
        return new  ScheduleGetResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getAuthor(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    //일정 수정
    @Transactional
    public ScheduleUpdateResponse update(Long scheduleId, ScheduleUpdateRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException(("해당 id의 일정이 없습니다."))
        );

        //null safe한 비밀번호 일치여부 확인
        if(!ObjectUtils.nullSafeEquals(schedule.getPassword(),request.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        //더티체킹
        schedule.updateTitleAuthor(
                request.getTitle(), request.getAuthor()
        );
        return new ScheduleUpdateResponse(
                schedule.getTitle(),
                schedule.getAuthor(),
                schedule.getPassword()
        );
    }

    //일정 삭제
    @Transactional
    public void deleteOne(Long scheduleId, String password) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException(("해당 id의 일정이 없습니다."))
        );
        if (!ObjectUtils.nullSafeEquals(password, schedule.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}