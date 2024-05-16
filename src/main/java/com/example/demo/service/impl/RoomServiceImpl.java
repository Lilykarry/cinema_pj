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
        room.setRoomId(generateRoomID());
        roomRepository.save(room);
    }

    @Override
    public List<Room> showAll() {
        return roomRepository.findAll();
    }

    public String generateRoomID() {
        String maxRoomID = roomRepository.findMaxRoomID();
        if (maxRoomID == null) {
            return "R01";
        }
        int currentNumber = Integer.parseInt(maxRoomID.substring(1));
        currentNumber++;
        return "R" + String.format("%02d", currentNumber);
    }
}
