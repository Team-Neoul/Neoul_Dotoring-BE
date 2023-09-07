package com.theZ.dotoring.app.room.domain;


import com.theZ.dotoring.app.letter.domain.Letter;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 쪽지함 엔티티
 *
 * @author Kevin
 * @version 1.0
 */
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

    /**
     * RoomEntity의 lastSendTime을 최신화 하는 메서드
     *
     */
    public void updateLastSendTime(){
        this.lastSendTime = LocalDateTime.now();
    }
}
