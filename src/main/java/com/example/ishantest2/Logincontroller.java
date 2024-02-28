//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.ishantest2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static java.sql.DriverManager.getConnection;

public class Logincontroller {
    public TextField email;
    public PasswordField pswd;
    public Label msg;
    public TextField username;
    public TextField id;

    public Logincontroller() {
    }

    public void LoginClick(ActionEvent actionEvent) {
        this.msg.setText("");
        String Email_add = this.email.getText();
        String Given_Password = this.pswd.getText();
        if (!Email_add.equals("") && !Given_Password.equals("")) {
            String jdbcUrl = "jdbc:mysql://localhost:3306/expenses";
            String dbUser = "root";
            String dbPassword = "";

            try {
                Connection connection = getConnection(jdbcUrl, dbUser, dbPassword);

                try {
                    String query = "SELECT * FROM `credentials_table` WHERE email='" + Email_add + "'";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String mail = resultSet.getString("email");
                        String password = resultSet.getString("password");
                        if (password.equals(Given_Password)) {
                            this.msg.setText("Success");

                            try {
                                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("hello-view.fxml"));
                                Parent secondScene = (Parent)loader.load();
                                HelloController userController = (HelloController)loader.getController();
                                Stage secondStage = new Stage();
                                secondStage.setTitle("Employee Scene");
                                secondStage.setScene(new Scene(secondScene));
                                Stage firstSceneStage = (Stage)this.pswd.getScene().getWindow();
                                firstSceneStage.close();
                                secondStage.show();
                            } catch (IOException var21) {
                                var21.printStackTrace();
                            }
                        } else {
                            this.msg.setText("Invalid email or password");
                        }
                    } else {
                        this.msg.setText("Invalid email or password");
                    }
                } catch (Throwable var22) {
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (Throwable var20) {
                            var22.addSuppressed(var20);
                        }
                    }

                    throw var22;
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException var23) {
                var23.printStackTrace();
            }
        } else {
            this.msg.setText("Please Give Email or Password");
        }

    }
}

