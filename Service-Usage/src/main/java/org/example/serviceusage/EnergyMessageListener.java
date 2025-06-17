package org.example.serviceusage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.serviceusage.EnergyMessage;
import org.example.serviceusage.UsageRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EnergyMessageListener {


    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UsageRepository repository;

    public EnergyMessageListener(UsageRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "energy")
    public void receive(String rawMessage) {
        try {
            EnergyMessage message = objectMapper.readValue(rawMessage, EnergyMessage.class);
            System.out.println("Empfangen: " + message);

            repository.processMessage(message); // ✅ Jetzt korrekt verfügbar

        } catch (Exception e) {
            System.err.println("Fehler beim Verarbeiten der Nachricht: " + e.getMessage());
        }
    }
}