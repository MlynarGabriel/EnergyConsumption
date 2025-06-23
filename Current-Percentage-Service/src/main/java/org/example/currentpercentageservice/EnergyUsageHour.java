package org.example.currentpercentageservice;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "energy_usage_hour")
public class EnergyUsageHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime hour;

    @Column(name = "community_produced")
    private double communityProduced;

    @Column(name = "community_used")
    private double communityUsed;

    @Column(name = "grid_used")
    private double gridUsed;

    @Column(name = "community_depleted")
    private Double communityDepleted;

    @Column(name = "grid_portion")
    private Double gridPortion;

    public EnergyUsageHour() {}

    // Getter und Setter
    public Long getId() { return id; }

    public LocalDateTime getHour() { return hour; }

    public void setHour(LocalDateTime hour) { this.hour = hour; }

    public double getCommunityProduced() { return communityProduced; }

    public void setCommunityProduced(double communityProduced) { this.communityProduced = communityProduced; }

    public double getCommunityUsed() { return communityUsed; }

    public void setCommunityUsed(double communityUsed) { this.communityUsed = communityUsed; }

    public double getGridUsed() { return gridUsed; }

    public void setGridUsed(double gridUsed) { this.gridUsed = gridUsed; }

    public Double getCommunityDepleted() { return communityDepleted; }

    public void setCommunityDepleted(Double communityDepleted) { this.communityDepleted = communityDepleted; }

    public Double getGridPortion() { return gridPortion; }

    public void setGridPortion(Double gridPortion) { this.gridPortion = gridPortion; }
}