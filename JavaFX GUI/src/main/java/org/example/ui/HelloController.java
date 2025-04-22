package org.example.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    private final ApiService apiService = new ApiService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @FXML
    public void initialize() {
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
        try {
            String json = apiService.getCurrentPercentage();
            JsonNode obj = objectMapper.readTree(json);

            double communityDepleted = obj.get("communityDepleted").asDouble();
            double gridPortion = obj.get("gridPortion").asDouble();

            poolUsageLabel.setText(String.format("Community Depleted: %.2f %%", communityDepleted));
            gridPortionLabel.setText(String.format("Grid Portion: %.2f %%", gridPortion));

        } catch (Exception e) {
            e.printStackTrace();
            poolUsageLabel.setText("Error loading data");
            gridPortionLabel.setText("Error loading data");
        }
    }

    @FXML
    public void showData() {
        try {
            String start = startDate.getValue().toString() + "T" + startTime.getValue();
            String end = endDate.getValue().toString() + "T" + endTime.getValue();

            String json = apiService.getHistoricalData(start, end);
            JsonNode array = objectMapper.readTree(json);

            if (array.isArray() && array.size() > 0) {
                JsonNode firstEntry = array.get(0); // get the first record

                double produced = firstEntry.get("communityProduced").asDouble();
                double used = firstEntry.get("communityUsed").asDouble();
                double grid = firstEntry.get("gridUsed").asDouble();

                communityProduced.setText(String.format("Community Produced: %.2f kWh", produced));
                communityUsed.setText(String.format("Community Used: %.2f kWh", used));
                gridUsed.setText(String.format("Grid Used: %.2f kWh", grid));
            } else {
                communityProduced.setText("No Data");
                communityUsed.setText("No Data");
                gridUsed.setText("No Data");
            }

        } catch (Exception e) {
            e.printStackTrace();
            communityProduced.setText("Error loading data");
            communityUsed.setText("Error loading data");
            gridUsed.setText("Error loading data");
        }
    }
}
