package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ChatbaseService {
    private final String CHATBASE_URL;
    private final String CHATBOT_ID;
    private final String API_KEY;

    ChatbaseService() {
        CHATBASE_URL = "https://www.chatbase.co/api/v1/chat";
        CHATBOT_ID = "CHATBOT_ID";
        API_KEY = "API_KEY";
    }

    public String sendMessage(String message) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(CHATBASE_URL))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(createJsonPayload(message)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    private String createJsonPayload(String message) {
        return String.format("{"
                + "\"messages\": [{\"content\": \"%s\", \"role\": \"user\"}],"
                + "\"chatbotId\": \"%s\","
                + "\"stream\": false,"
                + "\"temperature\": 0"
                + "}", message, CHATBOT_ID);
    }
}
