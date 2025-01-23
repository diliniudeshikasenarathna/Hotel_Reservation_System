package controller.Rooms;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Rooms;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class manageRoomFormController implements Initializable {

    @FXML
    private JFXComboBox cmbAvailability;

    @FXML
    private JFXComboBox cmbPrice;

    @FXML
    private JFXComboBox cmbRoomType;

    @FXML
    private TableColumn colPrice;

    @FXML
    private TableColumn colRoomNo;

    @FXML
    private TableColumn colRoomType;

    @FXML
    private TableColumn colStatus;

    @FXML
    private TableView tblManageRoom;

    @FXML
    private JFXTextField txtAvailability;

    @FXML
    private JFXTextField txtPricePerNight;

    @FXML
    private JFXTextField txtRoomNo;

    @FXML
    private JFXTextField txtType;

    @FXML
    void btnAddRoomOnAction(ActionEvent event) {
        try {
            String no=txtRoomNo.getText();
            String type=txtType.getText();
            Double price=Double.parseDouble(txtPricePerNight.getText());
            String status=txtAvailability.getText();

            Rooms rooms = new Rooms(no,type,price,status);
            String SQL="insert into rooms(room_number,room_type,price_per_night,availability_status) values(?,?,?,?)";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setString(1, rooms.getRoomNo());
            psTm.setString(2,rooms.getType());
            psTm.setString(3, String.valueOf(rooms.getPricePerNight()));
            psTm.setString(4,rooms.getStatus());
            psTm.executeUpdate();

            new Alert(Alert.AlertType.INFORMATION,"Room Added Sucessfully!!!").show();

            txtRoomNo.setText("");
            txtType.setText("");
            txtPricePerNight.setText("");
            txtAvailability.setText("");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadRoomDetails();

    }

    @FXML
    void btnDeleteRoomOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateRoomOnAction(ActionEvent event) {

    }

    @FXML
    void btnViewRoomOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadRoomDetails();

        loadRoomTypeList();
        loadRoomAvailabilityList();
        loadRoomPriceList();
    }




    private void loadRoomDetails() {

        ObservableList<Rooms> roomsObservableList = FXCollections.observableArrayList();
        new manageRoomController().getAll().forEach(rooms -> {
            roomsObservableList.add(rooms);
        });

        tblManageRoom.setItems(roomsObservableList);
    }

    private void loadRoomTypeList() {
        ObservableList<String> roomTypeObservableList = FXCollections.observableArrayList();
        roomTypeObservableList.add("Single");
        roomTypeObservableList.add("Double");
        roomTypeObservableList.add("Suite");

        cmbRoomType.setItems(roomTypeObservableList);
    }
    private void loadRoomPriceList() {
        ObservableList<Double> roomPriceObservableList = FXCollections.observableArrayList();

        new manageRoomController().getRoomPriceList().forEach(price->{
            roomPriceObservableList.add(price);
        });

        cmbPrice.setItems(roomPriceObservableList);
    }

    private void loadRoomAvailabilityList() {
        ObservableList<String> roomStatusObservableList = FXCollections.observableArrayList();
        roomStatusObservableList.add("Available");
        roomStatusObservableList.add("Occupied");

cmbAvailability.setItems(roomStatusObservableList);
    }
}
