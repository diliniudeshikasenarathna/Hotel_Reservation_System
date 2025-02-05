package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class dashboardFormController {

    @FXML
    private AnchorPane pageLoad;

    @FXML
    void btnCustomerOnClick(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/customer_form.fxml");
        assert resource!=null;
        Parent load = FXMLLoader.load(resource);
        pageLoad.getChildren().clear();
        pageLoad.getChildren().add(load);

    }

    @FXML
    void btnManageRoomOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/manage_room_form.fxml");
        assert resource!=null;
        Parent load = FXMLLoader.load(resource);
        pageLoad.getChildren().clear();
        pageLoad.getChildren().add(load);

    }

    @FXML
    void btnReportsOnClick(ActionEvent event) {

    }

    @FXML
    void btnReservationOnClick(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/reservation_form.fxml");
        assert resource!=null;
        Parent load = FXMLLoader.load(resource);
        pageLoad.getChildren().clear();
        pageLoad.getChildren().add(load);


    }

    public void btnBookingsOnClick(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/bookings_form.fxml");
        assert resource!=null;
        Parent load = FXMLLoader.load(resource);
        pageLoad.getChildren().clear();
        pageLoad.getChildren().add(load);
    }
}
