module com.example.ishantest2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.ishantest2 to javafx.fxml;
    exports com.example.ishantest2;
}