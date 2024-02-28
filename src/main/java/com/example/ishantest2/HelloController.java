package com.example.ishantest2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TableView<Expense> tableView;
    @FXML
    private TableColumn<Expense, Integer> expnseid;
    @FXML
    private TableColumn<Expense, String> expensetype;
    @FXML
    private TableColumn<Expense, Double> amount;
    @FXML
    private TableColumn<Expense, String> date;

    @FXML
    private TextField eExpid;
    @FXML
    private TextField eExptyp;
    @FXML
    private TextField eamt;
    @FXML
    private TextField edate;

    ObservableList<Expense> expensesList = FXCollections.observableArrayList();

    public HelloController() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expnseid.setCellValueFactory(new PropertyValueFactory<>("ExpensesID"));
        expensetype.setCellValueFactory(new PropertyValueFactory<>("ExpenseType"));
        amount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));

        tableView.setItems(expensesList);
    }

    @FXML
    protected void onHelloButtonClick() {
        populateTable();
    }

    public void populateTable() {
        expensesList.clear();
        String jdbcUrl = "jdbc:mysql://localhost:3306/expenses"; // Update the database name
        String dbUser = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM `expense_tracker`";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    int expensesID = resultSet.getInt("ExpensesID");
                    String expenseType = resultSet.getString("ExpenseType");
                    int amount = resultSet.getInt("Amount");
                    Date date = resultSet.getDate("Date");

                    expensesList.add(new Expense(expensesID, expenseType, amount, date.toString()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void InsertData() {
        String expenseType = eExptyp.getText();
        double amount = Double.parseDouble(eamt.getText());
        String date = edate.getText();

        InserTable(expenseType, amount, date);
        populateTable();
    }

    public void InserTable(String expenseType, double amount, String date) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/expenses"; // Update the database name
        String dbUser = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO `expense_tracker` (`ExpenseType`, `Amount`, `Date`) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, expenseType);
                statement.setDouble(2, amount);
                statement.setString(3, date);
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void DeleteData() {
        int expenseID = Integer.parseInt(eExpid.getText());

        DeleteTable(expenseID);
        populateTable();
    }

    public void DeleteTable(int expenseID) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/expenses"; // Update the database name
        String dbUser = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "DELETE FROM `expense_tracker` WHERE ExpensesID=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, expenseID);
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void UpdateData() {
        int expenseID = Integer.parseInt(eExpid.getText());
        String expenseType = eExptyp.getText();
        double amount = Double.parseDouble(eamt.getText());
        String date = edate.getText();

        UpdateTable(expenseID, expenseType, amount, date);
        populateTable();
    }

    public void UpdateTable(int expenseID, String expenseType, double amount, String date) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/expenses"; // Update the database name
        String dbUser = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "UPDATE `expense_tracker` SET `ExpenseType`=?, `Amount`=?, `Date`=? WHERE ExpensesID=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, expenseType);
                statement.setDouble(2, amount);
                statement.setString(3, date);
                statement.setInt(4, expenseID);
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void LoadData() {
        int expenseID = Integer.parseInt(eExpid.getText());

        LoadTable(expenseID);
    }

    public void LoadTable(int expenseID) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/expenses"; // Update the database name
        String dbUser = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM `expense_tracker` WHERE ExpensesID=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, expenseID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("ExpensesID");
                        String expenseType = resultSet.getString("ExpenseType");
                        double amount = resultSet.getDouble("Amount");
                        Date timestamp = resultSet.getDate("Date");

                        eExptyp.setText(expenseType);
                        eamt.setText(String.valueOf(amount));
                        edate.setText(timestamp.toString());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    }
