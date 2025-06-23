package org.example.currentpercentageservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.currentpercentageservice.PercentageCalculatorService;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PercentageMessageListener {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PercentageCalculatorService calculatorService;

    public PercentageMessageListener(PercentageCalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @RabbitListener(queuesToDeclare = @Queue("usage_updates"))
    public void receiveUpdate(String rawMessage) {
        try {
            UpdateMessage msg = objectMapper.readValue(rawMessage, UpdateMessage.class);
            calculatorService.calculatePercentages(msg.hour);
        } catch (Exception e) {
            System.err.println("Fehler beim Verarbeiten der Nachricht: " + e.getMessage());
        }
    }

    // internes DTO
    private static class UpdateMessage {
        public String hour;
    }
}