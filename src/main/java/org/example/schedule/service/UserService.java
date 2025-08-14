package org.example.schedule.service;

import lombok.RequiredArgsConstructor;
import org.example.schedule.config.PasswordEncoder;
import org.example.schedule.dto.*;
import org.example.schedule.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.example.schedule.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //유저 등록 (회원가입)
    @Transactional
    public UserSaveResponse save(String userName, String email, String password) {

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(password);

        // 암호화된 비밀번호로 유저 객체 생성
        User user = new User(userName, email, encodePassword);

        // 저장
        User savedUser = userRepository.save(user);

        // 응답 객체 반환
        return new UserSaveResponse(
                savedUser.getUserId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt(),
                "회원 가입이 완료되었습니다."
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
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        // 연관관계 포함해서 일정 업데이트
        user.updateUser(request.getUserName(), request.getEmail());

        return new UserUpdateResponse(
                request.getUserName(),
                request.getEmail(),
                "회원 정보가 성공적으로 수정되었습니다."
        );
    }


    //유저 정보 삭제
    @Transactional
    public void deleteOne(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("해당 id의 유저 정보가 없습니다.")
        );

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        userRepository.deleteById(userId);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
