module org.example.m03uf5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.m03uf5 to javafx.fxml;
    exports org.example.m03uf5;
    opens org.example.m03uf5.controller to javafx.fxml;
    exports org.example.m03uf5.controller;
}