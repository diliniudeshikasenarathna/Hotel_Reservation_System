package controller.Customers;


import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customers;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class customerFormController implements Initializable {

    @FXML
    public TextField txtId;
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtContact;
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
    void btnAddCustomerOnAction(ActionEvent event) throws SQLException {

        String contact1=txtContact.getText();

        String SQL2="select contact_details from customers";
        ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL2);
        List<String> contactList=new ArrayList<>();

        while (resultSet.next()){
            contactList.add(resultSet.getString(1));
        }

        if(!contactList.contains(contact1)){
            String name=txtName.getText();
            String contact=txtContact.getText();

            Customers customers = new Customers(name,contact);

            String SQL="insert into customers (name,contact_details) values (?,?)";
            PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            psTm.setString(1,  customers.getName());
            psTm.setString(2, customers.getContact());
            psTm.executeUpdate();

            new Alert(Alert.AlertType.INFORMATION,"Customer Added Successfully!!!!").show();

            loadCustomerDetails();

            loadCustomerId();
            txtName.setText("");
            txtContact.setText("");
        }
else {
    new Alert(Alert.AlertType.ERROR,"Customer Already exist..........").show();
        }





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

    public void btnGetCustomerReportOnAction(ActionEvent actionEvent) {


        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/reports/Hotel_Customer_Report.jrxml");


            /// excute query manual,only one customer search

          //  JRDesignQuery jrDesignQuery = new JRDesignQuery();
          //  jrDesignQuery.setText("select * from customers where customerid='C001'");
          //  design.setQuery(jrDesignQuery);

            /// ///

            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());

/// ///////auto matically save to file structure
            JasperExportManager.exportReportToPdfFile(jasperPrint,"customers_report.pdf");
/// ///////////////////////////
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
