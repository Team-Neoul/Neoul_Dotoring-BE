package com.theZ.dotoring.app.room.domain;


import com.theZ.dotoring.app.letter.domain.Letter;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Letter> letterList = new ArrayList<>();

    private String writerName;

    private String receiverName;

    @CreationTimestamp
    @Column(nullable = false, length = 20, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastSendTime;

    public void updateLastSendTime(){
        this.lastSendTime = LocalDateTime.now();
    }
}
