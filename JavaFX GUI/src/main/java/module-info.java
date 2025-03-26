module org.example.ui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.example.ui to javafx.fxml;
    exports org.example.ui;
}