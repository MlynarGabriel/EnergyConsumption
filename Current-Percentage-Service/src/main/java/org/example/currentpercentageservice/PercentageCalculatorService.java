package org.example.currentpercentageservice;

import org.example.currentpercentageservice.EnergyUsageHour;
import org.example.currentpercentageservice.EnergyUsageHourRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PercentageCalculatorService {

    private final EnergyUsageHourRepository repository;

    public PercentageCalculatorService(EnergyUsageHourRepository repository) {
        this.repository = repository;
    }

    public void calculatePercentages(String hourString) {
        LocalDateTime hour = LocalDateTime.parse(hourString);

        EnergyUsageHour data = repository.findByHour(hour)
                .orElseThrow(() -> new RuntimeException("Kein Datensatz für Stunde: " + hourString));

        double totalUsed = data.getCommunityUsed() + data.getGridUsed();
        double communityDepleted = (data.getCommunityProduced() == 0) ? 100.0 :
                Math.min(100.0, (data.getCommunityUsed() / data.getCommunityProduced()) * 100.0);
        double gridPortion = (totalUsed == 0) ? 0.0 : (data.getGridUsed() / totalUsed) * 100.0;

        data.setCommunityDepleted(communityDepleted);
        data.setGridPortion(gridPortion);
        repository.save(data);

        System.out.printf("Berechnet: %.2f%% verbraucht, Grid-Anteil: %.2f%%%n", communityDepleted, gridPortion);
    }
}