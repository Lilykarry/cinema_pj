package com.example.demo.service.impl;

import com.example.demo.model.Room;
import com.example.demo.model.Threat;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }

    @Override
    public List<Room> showAll() {
        return roomRepository.findAll();
    }


}
