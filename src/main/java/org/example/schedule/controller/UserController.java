package org.example.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.example.schedule.dto.*;
import org.example.schedule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<UserSaveResponse> signUp(@RequestBody UserSaveRequest requestDto) {

        UserSaveResponse userSaveResponse =
                userService.signUp(
                        requestDto.getUserName(),
                        requestDto.getEmail(),
                        requestDto.getPassword()
                );

        return new ResponseEntity<>(userSaveResponse, HttpStatus.CREATED);
    }

    //전체 유저 조회
    @GetMapping("/users")
    public ResponseEntity<List<UserGetAllResponse>> getUsers(
            @RequestParam(required = false) String userName
    ) {
        List<UserGetAllResponse> response = userService.findAll(userName);
        return ResponseEntity.ok(response);
    }


    //특정 유저 조회
    @GetMapping("/users/{id}")
    public ResponseEntity<UserGetResponse> findById(@PathVariable Long id) {
        UserGetResponse userGetResponse = userService.findById(id);
        return new ResponseEntity<>(userGetResponse, HttpStatus.OK);
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
    ){
        //controller -> service 호출
        userService.deleteOne(userId, request);
    }

}