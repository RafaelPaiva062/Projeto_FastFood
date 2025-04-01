module com.example.fastfood {
    requires java.sql;
    requires java.desktop;
    requires jbcrypt;

    opens com.example.fastfood to javafx.fxml;
    exports com.example.fastfood;

    opens com.example.fastfood.controllers to javafx.fxml;
    exports com.example.fastfood.controllers;

    opens com.example.fastfood.entites to javafx.fxml;
    exports com.example.fastfood.entites;

    opens com.example.fastfood.dao to javafx.fxml;
    exports com.example.fastfood.dao;

}