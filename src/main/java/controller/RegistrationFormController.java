package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Users;
import org.jasypt.util.text.BasicTextEncryptor;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegistrationFormController implements Initializable {

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private PasswordField txtPassword;

    @FXML
    void btnSubmitOnAction(ActionEvent event) throws SQLException {

        String SQL="INSERT into Users (username,password_hash,role) values (?,?,?)";


        if(txtPassword.getText().equals(txtConfirmPassword.getText())){

            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from users WHERE username="+"'"+txtUserName.getText()+"'");
            if(!resultSet.next()){


                String key= "5129#hCi";
                BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
                basicTextEncryptor.setPassword(key);



                Users user= new Users(txtUserName.getText(),txtPassword.getText(),cmbRole.getValue());
                PreparedStatement psTm = connection.prepareStatement(SQL);
                psTm.setString(1,user.getUserName());
                psTm.setString(2,basicTextEncryptor.encrypt(user.getPassword()));
                psTm.setString(3,user.getRole());
                psTm.executeUpdate();
                new Alert(Alert.AlertType.INFORMATION,"Registration Succesfully!!!").show();
                txtUserName.setText("");


            }else{
                new Alert(Alert.AlertType.ERROR,"Email already have account!!!!").show();
            }
            txtUserName.setText("");
            txtPassword.setText("");
            txtConfirmPassword.setText("");

        }else{

            new Alert(Alert.AlertType.ERROR,"Re enter password!!!").show();
            txtConfirmPassword.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRolBox();
    }

    private void loadRolBox() {

        ArrayList<String> roleList = new ArrayList<>();
        roleList.add("Admin");
        roleList.add("Staff");
        roleList.add("Manager");

        ObservableList<String> roleObservableList = FXCollections.observableArrayList();

        roleList.forEach(role->roleObservableList.add(role));
        cmbRole.setItems(roleObservableList);
    }
}

