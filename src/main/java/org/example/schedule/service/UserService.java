package org.example.schedule.service;

import lombok.RequiredArgsConstructor;
import org.example.schedule.dto.*;
import org.example.schedule.entity.Schedule;
import org.example.schedule.entity.User;
import org.springframework.transaction.annotation.Transactional;
import org.example.schedule.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserSaveResponse saveUser(UserSaveRequest request) {
        User user = new User(
                request.getUserName(),
                request.getEmail()
        );
        User savedUser = userRepository.save(user);
        return new UserSaveResponse(
                savedUser.getUserId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<UserGetAllResponse> findAll(String userName) {
        List<User> users = userRepository.findAll();
        List<UserGetAllResponse> dtos = new ArrayList<>();
        //author 파라미터가 존재하지 않는 경우
        if (userName == null){
            for (User user : users) {
                dtos.add(new UserGetAllResponse(
                        user.getUserId(),
                        user.getUserName(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                ));
            }
            return dtos;
        }
        //author 파라미터가 존재하는 경우
        for (User user : users) {
            if(userName.equals(user.getUserName())){
                dtos.add(new UserGetAllResponse(
                        user.getUserId(),
                        user.getUserName(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                ));
            }
        }
        return dtos;
    }

    //단건 유저 조회
    @Transactional(readOnly = true)
    public UserGetResponse findOne(long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException(("해당 id의 일정이 없습니다."))
        );//null인 id 처리
        return new UserGetResponse(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    //유저 정보 수정
    @Transactional
    public UserUpdateResponse update(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException(("해당 id의 유저가 없습니다."))
        );

        //더티체킹
        user.updateUser(
                request.getUserName(), request.getEmail()
        );
        return new UserUpdateResponse(
                user.getUserName(),
                user.getEmail()
        );
    }

    @Transactional
    public void deleteOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("해당 id의 유저 정보가 없습니다."));
        userRepository.deleteById(userId);
    }
}
