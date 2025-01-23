package db;


import lombok.Getter;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static DBConnection instance;
    @Getter
    public Connection connection;
    private DBConnection() throws SQLException {
        String URL="jdbc:mysql://localhost:3306/hotel_reservation_system";
        String root="root";
        String password="1234";
        connection = DriverManager.getConnection(URL, root, password);

    }
    public static DBConnection getInstance() throws SQLException {
        return instance==null?instance=new DBConnection():instance;
    }

}
