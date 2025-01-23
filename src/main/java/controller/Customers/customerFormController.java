package controller.Customers;

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
import model.Customers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class customerFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLoyalityPoints;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<Customers> tblCustomerDetails;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) throws SQLException {

        String name=txtName.getText();
        String contact=txtContact.getText();

        Customers customers = new Customers(name,contact);

        String SQL="insert into customers (name,contact_details) values (?,?)";
        PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
        psTm.setString(1,  customers.getName());
        psTm.setString(2, customers.getContact());
        psTm.executeUpdate();

        new Alert(Alert.AlertType.INFORMATION,"Customer Added Successfully!!!!");

        loadCustomerDetails();

        loadCustomerId();
        txtName.setText("");
        txtContact.setText("");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colLoyalityPoints.setCellValueFactory(new PropertyValueFactory<>("loyalityPoints"));
        loadCustomerDetails();

        loadCustomerId();

    }

    private void loadCustomerId() {
        String SQL= "select count(customer_id) from customers";
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            resultSet.next();
            txtId.setText(String.valueOf(resultSet.getInt(1)+1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerDetails() {
        ObservableList<Customers> customerDetailsList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("Select * from Customers");
            while (resultSet.next()){
                Customers customers= new Customers(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(1)
                );
                customerDetailsList.add(customers);

            }
            tblCustomerDetails.setItems(customerDetailsList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
