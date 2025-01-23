package controller.Rooms;

import model.Rooms;

import java.util.List;

public interface manageRoomService {

    boolean addRoom(Rooms rooms);
    boolean deleteRoom(String roomNo);
    boolean updateRoom(Rooms rooms);
    Rooms searchRoom(String roomNo);


    List<Rooms> getAll();
}
