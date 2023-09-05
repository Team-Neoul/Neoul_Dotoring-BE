package com.theZ.dotoring.app.letter.domain;

import com.theZ.dotoring.app.room.domain.Room;
import com.theZ.dotoring.common.CommonEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Letter extends CommonEntity {

    @Id
    @Column(name = "letter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String writerName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    // letterList에 추가하기
    public void addLetter(Room room){
        if (this.room != null) {
            this.room.getLetterList().remove(this);
        }
        this.room = room;
        if (room != null) {
            room.getLetterList().add(this);
        }
    }
}