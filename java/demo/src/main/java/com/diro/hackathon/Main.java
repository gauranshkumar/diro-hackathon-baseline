package com.diro.hackathon;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;

class APIHandler {
    public static HttpResponse<String> callAPI(String userInput, String... url) {
        {
            ArrayList<Map<String, String>> message = new ArrayList<Map<String, String>>();
            message.add(Map.of("role", "user", "content", userInput));
            Map<String, Object> reqMap = Map.of("model", "LLaMA_CPP", "messages", message);
            Gson gson = new Gson();
            String json = gson.toJson(reqMap);
            System.err.println(json.toString());

            if (url.length == 0) {
                url = new String[] { "http://localhost:7777/v1/chat/completions" };
            } else {
                url[0] = url[0] + "/v1/chat/completions";
            }

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url[0]))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer no-key")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("API Response: " + response.body());
                return response;
            } catch (Exception e) {
                System.out.println("Error during API request: " + e.getMessage());
            }
            return null;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your input for the OpenAI API:");
        APIHandler.callAPI(scanner.nextLine());
    }
}
