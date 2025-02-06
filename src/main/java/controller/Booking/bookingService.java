package controller.Booking;

import model.Bookings;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface bookingService {
    public List<Bookings> searchBooking(int reservationId);
    public boolean deleteBooking(int reservationId);
    public boolean releaseRoom(int reservationId);
    public boolean updateBooking(int reservationId, LocalDate inDate, LocalDate outDate);
    public Double calculateExtraCharges(int reservationId,LocalDate inDate,LocalDate outDate);
    public Double getTotalPrice(int reservationId);

}
