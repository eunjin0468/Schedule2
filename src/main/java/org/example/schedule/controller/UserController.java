package org.example.schedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schedule.config.PasswordEncoder;
import org.example.schedule.dto.*;
import org.example.schedule.entity.User;
import org.example.schedule.repository.UserRepository;
import org.example.schedule.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @PostMapping("/users/signup")
    public ResponseEntity<UserSaveResponse> signUp(@Valid @RequestBody UserSaveRequest requestDto) {
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
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다."));

        // 암호화된 비밀번호와 입력된 비밀번호 비교
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다.");
        }

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
        UserResponse userResponse = userService.findById(userId);

        return ResponseEntity.ok(userResponse);
    }

    //로그아웃
    @PostMapping("/users/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    //전체 유저 조회
    @GetMapping("/users")
    public ResponseEntity<List<UserGetAllResponse>> getUsers(
            @RequestParam(required = false) String userName,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"로그인이 필요합니다.");
        }
        List<UserGetAllResponse> response = userService.findAll(userName);
        return ResponseEntity.ok(response);
    }

    //유저 정보 수정 (세션에서 userId 추출)
    @PutMapping("/users")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @RequestBody UserUpdateRequest request,
            HttpServletRequest httpRequest
    ) {
        HttpSession session = httpRequest.getSession(false);
        if(session == null || session.getAttribute("userId") == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"로그인이 필요햡니다");
        }
        Long userId = (Long) session.getAttribute("userId");
        return ResponseEntity.ok(userService.update(userId, request));
    }

    //유저 정보 삭제 (세션에서 userId 추출)
    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUser(
            @RequestBody UserUpdateRequest request,
            HttpServletRequest httpRequest
    ) {
        HttpSession session = httpRequest.getSession(false);
        if(session == null || session.getAttribute("userId") == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"로그인이 필요합니다.");
        }
        Long userId = (Long) session.getAttribute("userId");
        userService.deleteOne(userId, request);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }
}