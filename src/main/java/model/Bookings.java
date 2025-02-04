package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Bookings {
    Integer reservationId;
    Integer customerId;
    Integer roomId;
    Date checkIn;
    Date checkOut;
    Double total;
    String status;
}
