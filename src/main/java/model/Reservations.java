package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Reservations {

    Integer cusId;
    Integer roomId;
    String checkInDate;
    String checkOutDate;
    Double total;
    String status;
}
