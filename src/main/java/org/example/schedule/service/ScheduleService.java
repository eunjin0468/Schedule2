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

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    
    @Transactional
    public ScheduleSaveResponse saveSchedule(ScheduleSaveRequest request) {
         Schedule schedule = new Schedule(
                 request.getTitle(),
                 request.getContent(),
                 request.getAuthor(),
                 request.getPassword()
         );
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

    @Transactional(readOnly = true)
    public List<ScheduleGetAllResponse> findAll(String author) {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleGetAllResponse> dtos = new ArrayList<>();
        //author 파라미터가 존재하지 않는 경우
        if (author == null){
            for (Schedule schedule : schedules) {
                dtos.add(new ScheduleGetAllResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getAuthor(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                ));
            }
            return dtos; //28:46 이 부분 optional로 바꿔보기! (43~54줄) 그니까 jpa 사용해보라
        }
        //author 파라미터가 존재하는 경우
        for (Schedule schedule : schedules) {
            if(author.equals(schedule.getAuthor())){
                dtos.add(new ScheduleGetAllResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getAuthor(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                ));
            }

        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public ScheduleGetResponse findOne(long scheduleId) {

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException(("해당 id의 일정이 없습니다."))
        );//null인 id 처리
        return new  ScheduleGetResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getAuthor(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

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
