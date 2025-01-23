package controller.Reservations;

import model.Reservations;
import model.Rooms;

import java.util.List;

public interface reservationService {
    boolean addReservations(Reservations reservations);
    boolean deleteReservations(String cusId, String roomId);
    boolean updateReservations(Reservations reservations);
    Reservations searchReservations(String cusId, String roomId);
    List<Reservations> getAll();
}
