package controller.Booking;

import model.Bookings;

import java.util.List;

public interface bookingService {
    public List<Bookings> searchBooking(int reservationId);
    public boolean deleteBooking(int reservationId);
    public boolean releaseRoom(int reservationId);

}
