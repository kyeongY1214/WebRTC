package com.d203.backend.api.service.Room;

import com.d203.backend.api.request.Room.RoomReq;
import com.d203.backend.api.request.Room.RoomUpadateReq;
import com.d203.backend.db.entity.Lang;
import com.d203.backend.db.entity.Room;
import com.d203.backend.db.repository.LangRepository;
import com.d203.backend.db.repository.RoomRepository;
import com.d203.backend.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("roomService")
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LangRepository langRepository;

    @Override
    public Room createRoom(RoomReq roomInfo) {
        Room room = new Room();

        room.setHostId(userRepository.getOne(roomInfo.getHostId()));
        room.setTopic(roomInfo.getTopic());
        room.setMaxnum(roomInfo.getMaxnum());
        room.setCurnum(roomInfo.getCurnum());
        room.setTopic(roomInfo.getTopic());
        room.setName(roomInfo.getName());


        room.setGuest_lang(langRepository.getOne(roomInfo.getGuest_lang()));
        room.setHost_lang(langRepository.getOne(roomInfo.getHost_lang()));

        room.setUuid(UUID.randomUUID().toString());

        return roomRepository.save(room);
    }

    @Override
    public boolean updateRoom(Long roomId, RoomUpadateReq updateRoomInfo) {
        Optional<Room> room = roomRepository.findById(roomId);

        Room updateRoom = room.get();

        updateRoom.setName(updateRoomInfo.getName());
        updateRoom.setTopic(updateRoomInfo.getTopic());
        updateRoom.setCurnum(updateRoomInfo.getCurnum());

        if (room.isPresent()) {
            roomRepository.save(updateRoom);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteRoom(Long room_id, Long tokenUserId) {
        Room delRoom = roomRepository.getOne(room_id);
        Long isAcceptId = delRoom.getHostId().getId();

        if (isAcceptId == tokenUserId) {
            roomRepository.delete(delRoom);
            return true;
        }

        return false;
    }

    @Override
    public Page<Room> getRoomList(String topic, String lang, int pageno) {
        Pageable firstPageWithTwoElements = PageRequest.of(pageno - 1, 5);
        long langId = 0;
        if (!lang.equals("")) {
            Lang findlang = langRepository.findByName(lang);
            langId = findlang.getId();
        }
        Page<Room> rooms = roomRepository.findAllBYLangAndTopic(topic, langId, firstPageWithTwoElements);
        return rooms;
    }

    @Override
    public Room getRoom(String room_uuid) {
        Room room = roomRepository.findByUuid(room_uuid);
        return room;
    }

    @Override
    public boolean getCheckJoin(String uuid) {
        Room room = roomRepository.findByUuid(uuid);
        //?????? ??? ?????? ???
        if (room == null) {
            return false;
        }
        //?????? ???????????? ?????? ?????? ?????? ?????? ??????
        if (room.getCurnum() < room.getMaxnum()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean doControlPeople(String uuid, Long people) {
        Room room = roomRepository.findByUuid(uuid);
        //????????? ?????? ?????? ?????? ??????
        if (room == null) {
            return false;
        }
        Long curnum = room.getCurnum();
        //??????????????? ???????????? ???????????? ???????????? ??????
        Long newNum = curnum + people;
        if (newNum > room.getMaxnum() || newNum <= 0) {
            return false;
        }
        room.setCurnum(newNum);
        roomRepository.save(room);
        return true;
    }

}
