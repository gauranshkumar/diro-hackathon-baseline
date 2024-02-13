package com.diro.hackathon;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;

// The APIHandler class is responsible for making requests to the API and parsing the response.
// It contains a method for making API requests and a method for parsing the response.

class APIHandler {

    // The message array contains the user input that is going to be serialised to
    // json with the help of GSON package.

    protected ArrayList<Map<String, String>> message = new ArrayList<Map<String, String>>();

    // The response object contains the response from the API.
    protected HttpResponse<String> response = null;

    // The API_URL and API_ENDPOINT are the base URL and endpoint for the API
    public final String API_URL = "http://localhost:7777";
    public final String API_ENDPOINT = "/v1/chat/completions";
    Gson gson = new Gson();
    ResponseModel responseJson = new ResponseModel();

    // The callAPI method sends a POST request to the API with the user input and
    // It takes the user input and an optional URL as arguments.
    public HttpResponse<String> callAPI(String userInput, String... url) {
        {
            // Add the user input to the message array
            this.message.add(Map.of("role", "user", "content", userInput));
            Map<String, Object> reqMap = Map.of("model", "LLaMA_CPP", "messages", message);

            // Serialise the message array to JSON
            Gson gson = new Gson();
            String json = gson.toJson(reqMap);

            // If no URL is provided, use the default API_URL and API_ENDPOINT
            if (url.length == 0) {
                url = new String[] { API_URL + API_ENDPOINT };
            } else {
                url[0] = url[0] + API_ENDPOINT;
            }

            // Send a POST request to the API with the JSON message as the body and headers
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url[0]))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer no-key")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            // Store the response from the API
            try {
                this.response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Parse the response body to a ResponseModel object using GSON
                this.responseJson = gson.fromJson(this.response.body(), ResponseModel.class);

                // Add the response from the API to the message array
                this.message.add(Map.of("role", this.responseJson.choices.get(0).message.role.strip(), "content",
                        this.responseJson.choices.get(0).message.content.strip()));

            } catch (Exception e) {
                System.out.println("Error during API request: " + e.getMessage());
            }

            return this.response;
        }
    }

    // The parseResp method parses the response from the API and returns the content
    public String parseResp() {

        return this.responseJson.choices.get(0).message.content.strip();
    }
}

public class Main {
    public static void main(String[] args) {

        // Create a new scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Create a new APIHandler object to make API requests
        APIHandler api = new APIHandler();

        // Print a welcome message and prompt the user for input
        System.out.println("Welcome to the Diro Chatbot, type 'exit' or 'quit' to exit.");

        // Loop to prompt the user for input and make API requests
        while (true) {
            System.out.print("Prompt> ");
            String userInput = scanner.nextLine();

            // If the user types 'exit' or 'quit', break the loop
            if (userInput.equals("exit") || userInput.equals("quit")) {
                break;
            }

            // Make an API request with the user input and print the response
            api.callAPI(userInput);
            System.out.println("Model>" + api.parseResp());
        }
        scanner.close();
    }
}
