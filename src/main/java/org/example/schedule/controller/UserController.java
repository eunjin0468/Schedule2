package org.example.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.example.schedule.dto.*;
import org.example.schedule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    //유저 생성
    @PostMapping("/users")
    public ResponseEntity<UserSaveResponse> saveUser(
            @RequestBody UserSaveRequest request
    ){
        //controller -> service 호출
        return ResponseEntity.ok(userService.saveUser(request));
    }

    //전체 유저 조회
    @GetMapping("/users")
    public ResponseEntity<List<UserGetAllResponse>> getUsers(
            @RequestParam(required = false) String userName //작성자명 포함 필수 아님
    ){
        //controller -> service 호출
        return ResponseEntity.ok(userService.findAll(userName));
    }

    //단건 유저 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserGetResponse> getUser(
            @PathVariable Long userId //null가능
    ){
        //controller -> service 호출
        return ResponseEntity.ok(userService.findOne(userId));
    }

    //유저 정보 수정
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateRequest request
    ){
        //controller -> service 호출
        return ResponseEntity.ok(userService.update(userId, request));
    }

    //유저 정보 삭제
    @DeleteMapping("/users/{userId}")
    public void deleteUser(
            @PathVariable Long userId
    ){
        //controller -> service 호출
        userService.deleteOne(userId);
    }
}
