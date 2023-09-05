package com.theZ.dotoring.app.letter.repository;

import com.theZ.dotoring.app.letter.domain.Letter;
import com.theZ.dotoring.app.room.domain.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

    Optional<Slice<Letter>> findByRoom(Room room, Pageable pageable);

    Optional<List<Letter>> findByRoom(Room room);

    @Query("select l from Letter l join fetch l.room where l.room = :room")
    Optional<List<Letter>> findByRoom1(@Param("room")Room room);

}