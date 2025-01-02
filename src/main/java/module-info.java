module org.example.m03uf5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.m03uf6 to javafx.fxml;
    exports org.example.m03uf6;
    opens org.example.m03uf6.controller to javafx.fxml;
    exports org.example.m03uf6.controller;
}