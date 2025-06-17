package com.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class EnergyUser {

    private static final String QUEUE_NAME = "energy";
    private static final String BROKER_URL = "localhost";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(BROKER_URL);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            ObjectMapper mapper = new ObjectMapper();
            Random random = new Random();

            while (true) {
                double baseUsage = 0.001 + random.nextDouble() * 0.004;
                double peakMultiplier = getPeakHourMultiplier(LocalDateTime.now().getHour());
                double kwh = baseUsage * peakMultiplier;

                Map<String, Object> message = new HashMap<>();
                message.put("type", "USER");
                message.put("association", "COMMUNITY");
                message.put("kwh", kwh);
                message.put("datetime", LocalDateTime.now().toString());

                String jsonMessage = mapper.writeValueAsString(message);
                channel.basicPublish("", QUEUE_NAME, null, jsonMessage.getBytes());
                System.out.println("Sent: " + jsonMessage);

                Thread.sleep(5000); // every 5 seconds
            }
        }
    }

    private static double getPeakHourMultiplier(int hour) {
        if ((hour >= 6 && hour <= 9) || (hour >= 17 && hour <= 21)) {
            return 2.0; // peak
        } else if ((hour >= 10 && hour <= 16)) {
            return 1.2; // moderate
        }
        return 0.7; // off-peak
    }
}
