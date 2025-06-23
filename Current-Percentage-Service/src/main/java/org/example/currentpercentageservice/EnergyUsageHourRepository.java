package org.example.currentpercentageservice;

import org.example.currentpercentageservice.EnergyUsageHour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EnergyUsageHourRepository extends JpaRepository<EnergyUsageHour, Long> {
    Optional<EnergyUsageHour> findByHour(LocalDateTime hour);
}
