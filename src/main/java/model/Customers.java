package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Customers {
    Integer id;
    String name;
    String contact;
    Integer loyaltyPoints;

    public Customers(String name, String contact) {
        this.contact = contact;
        this.name = name;
    }
}
