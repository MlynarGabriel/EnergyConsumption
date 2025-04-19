
package org.example.energyconsumption.dto;

import java.time.LocalDateTime;

public class HistoricalDto {

    private LocalDateTime hour;
    private double communityProduced;
    private double communityUsed;
    private double gridUsed;

    public HistoricalDto(LocalDateTime hour,
                         double communityProduced,
                         double communityUsed,
                         double gridUsed) {
        this.hour = hour;
        this.communityProduced = communityProduced;
        this.communityUsed = communityUsed;
        this.gridUsed = gridUsed;
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public double getCommunityProduced() {
        return communityProduced;
    }

    public double getCommunityUsed() {
        return communityUsed;
    }

    public double getGridUsed() {
        return gridUsed;
    }
}
