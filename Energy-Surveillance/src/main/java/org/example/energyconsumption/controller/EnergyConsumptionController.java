package org.example.energyconsumption.controller;

import org.example.energyconsumption.dto.CurrentEnergyDto;
import org.example.energyconsumption.dto.HistoricalDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/energy")
public class EnergyConsumptionController {

    @GetMapping("/current")
    public CurrentEnergyDto getCurrentEnergy() {

        LocalDateTime hour = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        double communityDepleted = 80.5; //AchTUNg Beispiel-Werte
        double gridPortion     = 19.5;

        return new CurrentEnergyDto(hour, communityDepleted, gridPortion);
    }

    @GetMapping("/historical")
    public HistoricalDto getHistoricalEnergy(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        // Beispiel-Daten summiert für den gesamten Zeitraum
        double communityProduced = 22.0; // z. B. Summe aus 10.0 + 12.0
        double communityUsed     = 19.0;
        double gridUsed          = 3.0;

        return new HistoricalDto(start, communityProduced, communityUsed, gridUsed);
    }
}
