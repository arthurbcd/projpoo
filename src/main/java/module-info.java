module com.proj {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.proj to javafx.fxml;
    exports com.proj;
}
