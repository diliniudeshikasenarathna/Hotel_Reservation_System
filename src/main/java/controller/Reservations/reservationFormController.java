package controller.Reservations;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class reservationFormController implements Initializable {

    public ComboBox cmbPrice;

    @FXML
    private ComboBox<String> cmbRoomNo;

    @FXML
    private ComboBox<String> cmbRoomType;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private DatePicker dateCheckIn;

    @FXML
    private DatePicker dateCheckOut;

    @FXML
    private Label lblPricePerNight;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblTotalDays;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtEmail;

    @FXML
    void btnSubmitOnAction(ActionEvent event) throws SQLException {
        int custId = Integer.parseInt(txtCustomerId.getText());

        String roomNumber=cmbRoomNo.getValue().toString();
        String sql= "select room_id from rooms where room_number="+"'"+roomNumber+"'";
        ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(sql);
        resultSet.next();

        int roomId=resultSet.getInt(1);
        LocalDate  checkIn = dateCheckIn.getValue();
        LocalDate checkout = dateCheckOut.getValue();
        Double total= Double.valueOf(lblTotal.getText());
        String status = cmbStatus.getValue();


        String SQL="INSERT into reservations (customer_id,room_id,check_in_date,check_out_date,total_amount,reservation_status) values(?,?,?,?,?,?)";

        PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
        psTm.setObject(1,custId);
        psTm.setObject(2,roomId);
        psTm.setObject(3,checkIn);
        psTm.setObject(4,checkout);
        psTm.setObject(5,total);
        psTm.setObject(6,status);
        boolean isBooked = psTm.executeUpdate()>0;
        if (isBooked){
            new Alert(Alert.AlertType.CONFIRMATION,"Reservation Completed!!!").show();
            cmbRoomType.getSelectionModel().clearSelection();
            cmbPrice.getSelectionModel().clearSelection();
            cmbRoomNo.getSelectionModel().clearSelection();
            cmbStatus.getSelectionModel().clearSelection();
            lblTotal.setText("");
            lblTotalDays.setText("");
            lblPricePerNight.setText("");
            dateCheckIn.setValue(null);
            dateCheckOut.setValue(null);
            txtCustomerId.setText("");
            txtCustomerName.setText("");
            txtEmail.setText("");


        }
        else{
            new Alert(Alert.AlertType.ERROR,"Reservation Failed").show();
        }

        String availableStatus="Occupied";
        String SQLROOMS="Update rooms set  availability_status="+"'"+availableStatus+"'"+ " where room_id="+roomId;
        DBConnection.getInstance().getConnection().createStatement().executeUpdate(SQLROOMS);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRoomType();
        setReservationStatus();

        cmbRoomType.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue!=null){
                try {
                    loadRoomPriceList(newValue.toString());

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }



    private void loadRoomPriceList(String type) throws SQLException {
        ArrayList<Double> priceList = new ArrayList<>();

        String roomType=type;
        String status="Available";
        String SQL = "Select distinct price_per_night from rooms where room_type="+ "'"+roomType+"'"+"and availability_status="+"'"+status+"'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);
            while (resultSet.next()){
                priceList.add(resultSet.getDouble(1));

            }

            ObservableList<String> observablePriceList = FXCollections.observableArrayList();
            priceList.forEach(price-> observablePriceList.add(String.valueOf(price)));
            cmbPrice.setItems(observablePriceList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String rtype = cmbRoomType.getValue();


        cmbPrice.getSelectionModel().selectedItemProperty().addListener((observableValue, o, nValue) -> {
            if(nValue!=null){
                loadRoomNoList(rtype,nValue.toString());

            }

        });

    }

  private void loadRoomNoList(String type, String price) {

    String roomType=type;
    Double roomPrice = Double.parseDouble(price);
      String status="Available";


     ArrayList<String> roomNoList = new ArrayList<>();
     String SQL= "select room_number from rooms where room_type="+"'"+roomType+"'"+" and price_per_night="+roomPrice +"and availability_status="+"'"+status+"'" ;
     try {
         Connection connection = DBConnection.getInstance().getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(SQL);

          while (resultSet.next()){
              roomNoList.add(resultSet.getString(1));
         }
         ObservableList<String> roomNoObservableList = FXCollections.observableArrayList();
          roomNoList.forEach(room->{
              roomNoObservableList.add(room);
          });
          cmbRoomNo.setItems(roomNoObservableList);
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
  }


    private void loadRoomType() {
        ObservableList<String> roomTypeObservableList = FXCollections.observableArrayList();
        roomTypeObservableList.add("Single");
        roomTypeObservableList.add("Double");
        roomTypeObservableList.add("Suite");

        cmbRoomType.setItems(roomTypeObservableList);



    }

    private void setReservationStatus(){
        ObservableList<String> reservationStatusList = FXCollections.observableArrayList();
        reservationStatusList.add("Confirmed");
        reservationStatusList.add("Pending");
        reservationStatusList.add("Canceled");
        cmbStatus.setItems(reservationStatusList);

    }


    public void btnCalculateOnAction(ActionEvent actionEvent) {
        LocalDate  checkIn = dateCheckIn.getValue();
        LocalDate checkOut = dateCheckOut.getValue();
        long daysBetween = ChronoUnit.DAYS.between(checkIn, checkOut);

        lblTotalDays.setText(String.valueOf(daysBetween));
        lblPricePerNight.setText(cmbPrice.getValue().toString());

        Double price=Double.parseDouble(cmbPrice.getValue().toString());


        Double days= (double) daysBetween;


        Double totalPrice= price*days;
        lblTotal.setText(totalPrice.toString());
        
    }
    
    private void loadCustomerDetails() throws SQLException {

        int custId = Integer.parseInt(txtCustomerId.getText());

        String SQL= "Select name,contact_details from customers where customer_id="+custId;

        Connection connection = DBConnection.getInstance().getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(SQL);
        resultSet.next();
        txtCustomerName.setText(resultSet.getString(1));
        txtEmail.setText(resultSet.getString(2));
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {

        try {
            loadCustomerDetails();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
