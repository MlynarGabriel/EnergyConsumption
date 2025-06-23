package org.example.serviceusage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EnergyMessageListener {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UsageRepository repository;

    public EnergyMessageListener(UsageRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queuesToDeclare = @org.springframework.amqp.rabbit.annotation.Queue("energy"))
    public void receive(String rawMessage) {
        try {
            EnergyMessage message = objectMapper.readValue(rawMessage, EnergyMessage.class);
            System.out.println("Empfangen: " + message);
            repository.processMessage(message);
        } catch (Exception e) {
            System.err.println("Fehler beim Verarbeiten der Nachricht: " + e.getMessage());
        }
    }
}
