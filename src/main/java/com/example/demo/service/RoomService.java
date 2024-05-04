package com.example.demo.service;

import com.example.demo.model.Room;

import java.util.List;

public interface RoomService {
    void save(Room room);

    List<Room> showAll();
}
