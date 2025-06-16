package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EnergyProducer {
    private static final String QUEUE_NAME = "energy";
    private static final String BROKER_URL = "localhost";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(BROKER_URL);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            ObjectMapper mapper = new ObjectMapper();
            Random random = new Random();

            while (true) {
                double baseProduction = 0.001 + random.nextDouble() * 0.005;
                double solarFactor = getSolarFactor(LocalDateTime.now().getHour());
                double kwh = baseProduction * solarFactor;

                Map<String, Object> message = new HashMap<>();
                message.put("type", "PRODUCER");
                message.put("association", "COMMUNITY");
                message.put("kwh", kwh);
                message.put("datetime", LocalDateTime.now().toString());

                String jsonMessage = mapper.writeValueAsString(message);
                channel.basicPublish("", QUEUE_NAME, null, jsonMessage.getBytes());
                System.out.println("Sent: " + jsonMessage);

                Thread.sleep(5000);
            }
        }
    }

    private static double getSolarFactor(int hour) {
        if (hour >= 10 && hour <= 15) return 1.8;     // Mittags viel Sonne
        if (hour >= 7 && hour <= 9 || hour >= 16 && hour <= 18) return 1.0;
        return 0.2;  // Nacht oder fast dunkel
    }
}