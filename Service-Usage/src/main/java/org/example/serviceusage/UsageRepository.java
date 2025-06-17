package org.example.serviceusage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsageRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void processMessage(EnergyMessage msg) {
        // Logik hier einbauen
    }
}

