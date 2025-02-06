package controller.Booking;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Bookings;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    @Override
    public boolean updateBooking(int reservationId, LocalDate inDate, LocalDate outDate) {

        String SQL="Insert into bookings values(?,?,?)";
        try {
            PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            psTm.setObject(1,reservationId);
            psTm.setObject(2,inDate);
            psTm.setObject(3,outDate);
            psTm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Double getpriceOfRoom(int roomid){
        String SQL="select price_per_night from rooms where room_id="+roomid;
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            resultSet.next();
            Double price=resultSet.getDouble(1);
            return price;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Double calculateExtraCharges(int reservationId,LocalDate inDate, LocalDate outDate) {

        String SQL="select room_id,check_in_date,check_out_date from reservations where reservation_id="+reservationId;

        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            resultSet.next();
            int roomId = resultSet.getInt(1);
            LocalDate checkInDate = resultSet.getDate(2).toLocalDate();
            LocalDate checkOutDate = resultSet.getDate(3).toLocalDate();
            Double reserveDays = (double) ChronoUnit.DAYS.between(checkInDate, checkOutDate);

            Double actualDays = (double) ChronoUnit.DAYS.between(inDate, outDate);

            Double roomPrice=getpriceOfRoom(roomId);
            Double additionalCharge = 0.0;

            if(reserveDays==actualDays){
                additionalCharge=0.0;

            }
            else if(actualDays>reserveDays){
                additionalCharge=(actualDays-reserveDays)*roomPrice;
            }

            return  additionalCharge;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public Double getTotalPrice(int reservationId) {
        String SQL="Select total_amount from reservations where reservation_id=" + reservationId;
        Double totalAmout;

        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            resultSet.next();
            totalAmout=resultSet.getDouble(1);
            return totalAmout;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public boolean insertActualCheckingDate(int reservationId, LocalDate inDate) {
        String SQL="Insert into bookings values(?,?)";
        try {
            PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            psTm.setObject(1,reservationId);
            psTm.setObject(2,inDate);
            return psTm.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean updateCheckOutDate(int reservationId, LocalDate outDate) {
        String SQL="update bookings set out_date="+"'"+outDate+"'"+"where reservation_id="+reservationId;
        try {
            int i = DBConnection.getInstance().getConnection().createStatement().executeUpdate(SQL);
            return i>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public LocalDate getCheckInDate(int reservationId){
        String SQL="select in_date from bookings where reservation_id="+reservationId;
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            resultSet.next();

            LocalDate inDate= resultSet.getDate(1).toLocalDate();
            return inDate;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
