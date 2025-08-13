package org.example.schedule.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.schedule.dto.*;
import org.example.schedule.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.example.schedule.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //유저 등록 (회원가입)
    @Transactional
    public UserSaveResponse save(String userName, String email, String password) {
        User user = new User(userName, email, password);
        User savedUser = userRepository.save(user);

        return new UserSaveResponse(
                savedUser.getUserId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt()
        );
    }

    //전체 유저 정보 조회
    @Transactional(readOnly = true)
    public List<UserGetAllResponse> findAll(String userName) {
        List<User> users = userRepository.findAll();
        List<UserGetAllResponse> dtos = new ArrayList<>();

        for (User user : users) {
            // userName 필터링
            if (userName == null || user.getUserName().equals(userName)) {
                List<ScheduleSimpleResponse> scheduleDtos = user.getSchedules()
                        .stream()
                        .map(schedule -> new ScheduleSimpleResponse(
                                schedule.getScheduleId(),
                                schedule.getTitle(),
                                schedule.getContent()
                        ))
                        .toList();

                dtos.add(new UserGetAllResponse(
                        user.getUserId(),
                        user.getUserName(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getModifiedAt(),
                        scheduleDtos
                ));
            }
        }
        return dtos;
    }

    public UserResponse findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."));
        return new UserResponse(user.getUserId(), user.getUserName(), user.getEmail(), user.getCreatedAt(), user.getModifiedAt());
    }


    //유저 정보 수정
    @Transactional
    public UserUpdateResponse update(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 id의 유저가 없습니다.")
        );

        // 비밀번호 검증
        if (!ObjectUtils.nullSafeEquals(user.getPassword(), request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        // 연관관계 포함해서 일정 업데이트
        user.updateUser(request.getUserName(), request.getEmail());

        return new UserUpdateResponse(
                request.getUserName(),
                request.getEmail()
        );
    }


    //유저 정보 삭제
    @Transactional
    public void deleteOne(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("해당 id의 유저 정보가 없습니다.")
        );

        // 비밀번호 검증
        if (!ObjectUtils.nullSafeEquals(user.getPassword(), request.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        userRepository.deleteById(userId);
    }
}
