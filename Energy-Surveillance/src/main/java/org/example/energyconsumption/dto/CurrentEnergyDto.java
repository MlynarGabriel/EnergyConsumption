
package org.example.energyconsumption.dto;

import java.time.LocalDateTime;

public class CurrentEnergyDto {

    private LocalDateTime hour;
    private double communityDepleted;
    private double gridPortion;

    public CurrentEnergyDto(LocalDateTime hour, double communityDepleted, double gridPortion) {
        this.hour = hour;
        this.communityDepleted = communityDepleted;
        this.gridPortion = gridPortion;
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public double getCommunityDepleted() {
        return communityDepleted;
    }

    public double getGridPortion() {
        return gridPortion;
    }
}
