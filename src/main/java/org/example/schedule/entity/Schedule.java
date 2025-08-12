package org.example.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "longtext")
    private String content;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Schedule(
            String title,
            String content
    ){
        this.title = title;
        this.content = content;
    }

    public void updateSchedule(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }
}