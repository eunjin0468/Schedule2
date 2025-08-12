package org.example.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;
    private String email;

    public User(
            String userName,
            String email
    ){
        this.userName = userName;
        this.email = email;
    }

    //update method
    public void updateUser(String userName, String email){
        this.userName = userName;
        this.email = email;
    }
}
