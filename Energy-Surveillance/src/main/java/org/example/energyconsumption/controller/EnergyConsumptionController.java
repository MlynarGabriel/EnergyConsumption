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
    public List<HistoricalDto> getHistoricalEnergy(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        // BEISPIEL WERTE
        LocalDateTime h1 = start.truncatedTo(ChronoUnit.HOURS);
        LocalDateTime h2 = h1.plusHours(1).isBefore(end) ? h1.plusHours(1) : null;

        HistoricalDto first  = new HistoricalDto(h1, 10.0,  8.0,  2.0);
        HistoricalDto second = h2 != null ? new HistoricalDto(h2, 12.0, 11.0, 1.0)
                : null;

        return second != null
                ? List.of(first, second)
                : List.of(first);
    }
}
