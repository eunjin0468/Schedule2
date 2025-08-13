package org.example.schedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.example.schedule.dto.*;
import org.example.schedule.entity.User;
import org.example.schedule.repository.UserRepository;
import org.example.schedule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/users/signup")
    public ResponseEntity<UserSaveResponse> signUp(@RequestBody UserSaveRequest requestDto) {
        UserSaveResponse userSaveResponse =
                userService.save(
                        requestDto.getUserName(),
                        requestDto.getEmail(),
                        requestDto.getPassword()
                );

        return new ResponseEntity<>(userSaveResponse, HttpStatus.CREATED);
    }

    //로그인
    @PostMapping("/users/signin")
    public ResponseEntity<String> signin(@RequestBody SigninRequest request, HttpServletRequest httpRequest) {
        User user = userRepository.findByEmailAndUserName(request.getEmail(), request.getUserName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."));

        // 비밀번호 체크 대신 이메일과 userName으로 로그인 승인 (요구사항에 맞게)
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("userId", user.getUserId());

        return ResponseEntity.ok("로그인 성공");
    }

    //특정 유저 정보 조회
    @GetMapping("/users/me")
    public ResponseEntity<UserResponse> getMyInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."));

        return ResponseEntity.ok(new UserResponse(user.getUserId(), user.getUserName(), user.getEmail(), user.getCreatedAt(),user.getModifiedAt()));
    }

    //로그아웃
    @PostMapping("/users/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    //전체 유저 조회
    @GetMapping("/users")
    public ResponseEntity<List<UserGetAllResponse>> getUsers(
            @RequestParam(required = false) String userName
    ) {
        List<UserGetAllResponse> response = userService.findAll(userName);
        return ResponseEntity.ok(response);
    }

    //유저 정보 수정
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateRequest request
    ) {
        //controller -> service 호출
        return ResponseEntity.ok(userService.update(userId, request));
    }

    //유저 정보 삭제
    @DeleteMapping("/users/{userId}")
    public void deleteUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateRequest request
    ) {
        //controller -> service 호출
        userService.deleteOne(userId, request);
    }
}