package org.example.energyconsumption.controller;

import org.example.energyconsumption.dto.CurrentEnergyDto;
import org.example.energyconsumption.dto.HistoricalDto;
import org.example.energyconsumption.entity.EnergyUsageHour;
import org.example.energyconsumption.repository.EnergyRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/energy")
public class EnergyConsumptionController {

    private final EnergyRepository energyUsageRepository;

    public EnergyConsumptionController(EnergyRepository energyUsageRepository) {
        this.energyUsageRepository = energyUsageRepository;
    }

    @GetMapping("/current")
    public CurrentEnergyDto getCurrentEnergy() {
        LocalDateTime hour = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        EnergyUsageHour usage = energyUsageRepository.findByHour(hour);
        if (usage == null) {
            return new CurrentEnergyDto(hour, 0.0, 0.0); // oder andere Defaults
        }


        double communityProduced = usage.getCommunityProduced();
        double communityUsed = usage.getCommunityUsed();
        double gridUsed = usage.getGridUsed();

        double communityDepleted = communityProduced == 0 ? 100 : Math.min(100.0, (communityUsed / communityProduced) * 100);
        double gridPortion = (gridUsed / (communityUsed + gridUsed)) * 100;

        return new CurrentEnergyDto(hour, communityDepleted, gridPortion);
    }

    @GetMapping("/historical")
    public HistoricalDto getHistoricalEnergy(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        List<EnergyUsageHour> entries = energyUsageRepository.findByHourBetweenOrderByHour(start, end);

        double totalProduced = 0.0;
        double totalUsed = 0.0;
        double totalGridUsed = 0.0;

        for (EnergyUsageHour entry : entries) {
            totalProduced += entry.getCommunityProduced();
            totalUsed += entry.getCommunityUsed();
            totalGridUsed += entry.getGridUsed();
        }

        return new HistoricalDto(start, totalProduced, totalUsed, totalGridUsed);
    }


} 
