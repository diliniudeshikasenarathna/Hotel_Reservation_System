package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.TouchEvent;
import javafx.stage.Stage;
import model.Users;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class loginFormController {

    @FXML
    private PasswordField txtPassWord;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws SQLException, IOException {
        String SQL="SELECT * from users WHERE username="+"'"+txtUserName.getText()+"'";

        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);

        if(resultSet.next()){
            Users user=  new Users(resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));


            String key= "5129#hCi";
            BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
            basicTextEncryptor.setPassword(key);
            String decryptPassword = basicTextEncryptor.decrypt(user.getPassword());



            if(decryptPassword.equals(txtPassWord.getText())){

                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/dashboard_form.fxml")))));
                stage.show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Enter Correct Password").show();
            }


        }else {
            new Alert(Alert.AlertType.ERROR,"User not found!!!").show();
        }

    }

    @FXML
    void btnSignupOnAction(ActionEvent event) throws IOException {
        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/registration_form.fxml"))));
        stage.show();
    }



}

