package org.example.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class HelloController {

    @FXML private Label poolUsageLabel;
    @FXML private Label gridPortionLabel;

    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private ComboBox<String> startTime;
    @FXML private ComboBox<String> endTime;

    @FXML private Label communityProduced;
    @FXML private Label communityUsed;
    @FXML private Label gridUsed;

    @FXML
    public void initialize() {
        // Zeitoptionen vorbereiten
        for (int h = 0; h < 24; h++) {
            String time = String.format("%02d:00", h);
            startTime.getItems().add(time);
            endTime.getItems().add(time);
        }

        startDate.setValue(LocalDate.now());
        endDate.setValue(LocalDate.now());
        startTime.setValue("14:00");
        endTime.setValue("14:00");

        refreshData();
    }

    @FXML
    public void refreshData() {
        poolUsageLabel.setText("Hier mit Controller Klasse arbeiten");
        gridPortionLabel.setText("Hier mit Controller Klasse arbeiten");
    }

    @FXML
    public void showData() {
        // Daten anzeigen (Simuliert)
        communityProduced.setText("Community produced: Hier mit Controller Klasse arbeiten");
        communityUsed.setText("Community used: Hier mit Controller Klasse arbeiten");
        gridUsed.setText("Grid used: Hier mit Controller Klasse arbeiten");
    }
}
