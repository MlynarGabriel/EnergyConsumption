package org.example.energyconsumption.controller;

import org.example.energyconsumption.entity.EnergyConsumption;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class EnergyConsumptionController {

    @GetMapping("/energy/current")
    public void getCurrentEnergyConsumption() {

    }

    @GetMapping("/energy/historical")
    public EnergyConsumption getHistoricalEnergyConsumption(@RequestParam LocalDateTime start,
                                                            @RequestParam LocalDateTime end) {


        EnergyConsumption consumption = new EnergyConsumption(1,2,3);
        return consumption;
    }


}
