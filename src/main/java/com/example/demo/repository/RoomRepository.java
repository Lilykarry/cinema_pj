package com.example.demo.repository;

import com.example.demo.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room,String> {

    List<Room> findAllByRoomIdIn(Collection<String> roomIds);
    List<Room> findAllByThreatId_Id(Integer threatId);
    @Query("SELECT MAX(r.roomId) FROM Room r")
    String findMaxRoomID();
}
