package controller.Rooms;

import db.DBConnection;
import model.Rooms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class manageRoomController implements  manageRoomService{


    @Override
    public boolean addRoom(Rooms rooms) {


        return false;
    }

    @Override
    public boolean deleteRoom(String roomNo) {
        return false;
    }

    @Override
    public boolean updateRoom(Rooms rooms) {
        return false;
    }

    @Override
    public Rooms searchRoom(String roomNo) {
        return null;
    }


    @Override
    public List<Rooms> getAll() {
        List<Rooms> roomList = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("Select * from rooms;");
            while (resultSet.next()){
                Rooms rooms = new Rooms(resultSet.getString(2),
                        resultSet.getString(3),resultSet.getDouble(4),resultSet.getString(5));
                roomList.add(rooms);
            }
            return roomList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Double> getRoomPriceList(){
        List<Double> roomPriceList=new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("select distinct price_per_night from rooms;");
            while (resultSet.next()){
                roomPriceList.add(resultSet.getDouble(1));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


return roomPriceList;
    }
}
