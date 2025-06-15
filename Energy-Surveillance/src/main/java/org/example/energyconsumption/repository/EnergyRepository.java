package org.example.energyconsumption.repository;

import org.example.energyconsumption.entity.EnergyUsageHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EnergyRepository extends JpaRepository<EnergyUsageHour, LocalDateTime> {

    List<EnergyUsageHour> findByHourBetweenOrderByHour(LocalDateTime start, LocalDateTime end);

    EnergyUsageHour findByHour(LocalDateTime hour);  // for /current
}
