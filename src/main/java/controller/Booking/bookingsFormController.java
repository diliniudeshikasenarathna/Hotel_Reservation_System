package controller.Booking;


import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Bookings;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class bookingsFormController implements Initializable {

    public TextField txtReservationID;
    @FXML
    private TableColumn<?, ?> colCheckIn;

    @FXML
    private TableColumn<?, ?> colCheckOut;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colReservationId;

    @FXML
    private TableColumn<?, ?> colRoomId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private DatePicker dateCheckIn;

    @FXML
    private DatePicker dateCheckOut;

    @FXML
    private Label lblEarlyCheckCharges;

    @FXML
    private Label lblLateCheckOutChragers;

    @FXML
    private Label lblTotalCharges;

    @FXML
    private TableView<Bookings> tblReservation;



    @FXML
    void btnAdditionalChargersOnAction(ActionEvent event) {

    }

    @FXML
    void btnReleaseRoomOnAction(ActionEvent event) {
        int  reservationId = Integer.parseInt(txtReservationID.getText());
        if(new bookingController().releaseRoom(reservationId)){
            new Alert(Alert.AlertType.CONFIRMATION,"Room Release Successfully!!!!").show();
        }

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        int  reservationId = Integer.parseInt(txtReservationID.getText());

        ObservableList<Bookings> bookingDetails = FXCollections.observableArrayList();
        new bookingController().searchBooking(reservationId).forEach(bookings -> {
            bookingDetails.add(bookings);
        });


            tblReservation.setItems(bookingDetails);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    }
}
