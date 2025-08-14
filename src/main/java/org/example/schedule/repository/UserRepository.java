package org.example.schedule.repository;

import org.example.schedule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //Optional<User> findUserByUserId(Long userId); // 인터페이스 메서드
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long userId);

    default User findByIdOrElseThrow(Long userId) {
        return findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with userId = " + userId));
    }
}