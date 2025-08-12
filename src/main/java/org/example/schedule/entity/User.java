package org.example.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String userName;
    private String email;

    @Column(nullable = false)
    private String password;

    //양방향일경우
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private final List<Schedule> schedules = new ArrayList<>();

    public User(
            String userName,
            String email,
            String password
    ){
        this.userName = userName;
        this.email = email;
        this.password = password;
    }



    //update method
    public void updateUser(String userName, String email){
        this.userName = userName;
        this.email = email;
    }
}
