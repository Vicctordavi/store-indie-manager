module org.victor.javagui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.victor.javagui to javafx.fxml;
    exports org.victor.javagui;
}