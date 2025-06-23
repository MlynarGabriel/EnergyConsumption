package org.example.serviceusage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UsageRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UsageRepository(JdbcTemplate jdbcTemplate, RabbitTemplate rabbitTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void processMessage(EnergyMessage msg) {
        try {
            LocalDateTime datetime = LocalDateTime.parse(msg.getDatetime());
            LocalDateTime hour = datetime.truncatedTo(ChronoUnit.HOURS);

            // Zeile erzeugen, falls nicht vorhanden
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM energy_usage_hour WHERE hour = ?",
                    Integer.class, hour);

            if (count != null && count == 0) {
                jdbcTemplate.update(
                        "INSERT INTO energy_usage_hour (hour, community_produced, community_used, grid_used) VALUES (?, 0, 0, 0)",
                        hour);
            }

            if ("PRODUCER".equalsIgnoreCase(msg.getType()) &&
                    "COMMUNITY".equalsIgnoreCase(msg.getAssociation())) {
                jdbcTemplate.update(
                        "UPDATE energy_usage_hour SET community_produced = community_produced + ? WHERE hour = ?",
                        msg.getKwh(), hour);
            } else if ("USER".equalsIgnoreCase(msg.getType()) &&
                    "COMMUNITY".equalsIgnoreCase(msg.getAssociation())) {

                double produced = jdbcTemplate.queryForObject(
                        "SELECT community_produced FROM energy_usage_hour WHERE hour = ?",
                        Double.class, hour);
                double used = jdbcTemplate.queryForObject(
                        "SELECT community_used FROM energy_usage_hour WHERE hour = ?",
                        Double.class, hour);

                double newUsed = used + msg.getKwh();
                double gridNeeded = Math.max(0, newUsed - produced);

                jdbcTemplate.update(
                        "UPDATE energy_usage_hour SET community_used = ?, grid_used = ? WHERE hour = ?",
                        newUsed, gridNeeded, hour);
            }


            Map<String, String> updateMessage = new HashMap<>();
            updateMessage.put("hour", hour.toString());
            String json = objectMapper.writeValueAsString(updateMessage);

            rabbitTemplate.convertAndSend("", "usage_updates", json);
            System.out.println("Gesendet an usage_updates: " + json);

        } catch (Exception e) {
            System.err.println("Fehler in processMessage: " + e.getMessage());
        }
    }
}
