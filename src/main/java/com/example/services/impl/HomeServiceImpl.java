package com.example.services.impl;

import com.example.dto.DtoHome;
import com.example.dto.DtoRoom;
import com.example.entities.Home;
import com.example.entities.Room;
import com.example.repository.HomeRepository;
import com.example.services.IHomeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HomeServiceImpl implements IHomeService {


    @Autowired
    private HomeRepository homeRepository;

    @Override
    public DtoHome findHomeById(Long id) {
        DtoHome dtoHome = new DtoHome();
        Optional<Home> optional = homeRepository.findById(id);
        if (optional.isEmpty()){
            return null;
        }
        Home dbHome = optional.get();
        List<Room> dbRooms= optional.get().getRoom();

        BeanUtils.copyProperties(dbHome, dtoHome);
        if (dbRooms!=null && !dbRooms.isEmpty()){
            for (Room room : dbRooms){
                DtoRoom dtoRoom = new DtoRoom();
                BeanUtils.copyProperties(room, dtoRoom);
                dtoHome.getRooms().add(dtoRoom);
            }
        }

        return dtoHome;
    }
}
