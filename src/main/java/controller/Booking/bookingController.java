package controller.Booking;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Bookings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class bookingController implements bookingService{
    @Override
    public List<Bookings> searchBooking(int reservationId) {
        List<Bookings> bookingDetails = new ArrayList<>();
        String SQL = "Select * from reservations where reservation_id=" + reservationId;

        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            resultSet.next();


            Bookings bookings = new Bookings(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getDate(4),
                    resultSet.getDate(5),
                    resultSet.getDouble(6),
                    resultSet.getString(7));
            bookingDetails.add(bookings);
            return bookingDetails;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteBooking(int reservationId) {
        return false;
    }

    @Override
    public boolean releaseRoom(int reservationId) {

        String SQL="Select room_id from reservations where reservation_id=" + reservationId;
        int roomId;

        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            resultSet.next();
            roomId=resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String status="Available";
        String sql="Update rooms set availability_status="+"'"+status+"'"+"where room_id="+roomId;
        try {
            boolean isReleaseRoom = DBConnection.getInstance().getConnection().createStatement().executeUpdate(sql)>0;
            return isReleaseRoom;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }


}
