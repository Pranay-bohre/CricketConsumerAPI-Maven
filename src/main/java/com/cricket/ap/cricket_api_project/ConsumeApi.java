package com.cricket.ap.cricket_api_project;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class ConsumeApi {
    public static String fetchCricketData() throws Exception {
        String apiUrl = "https://api.cuvora.com/car/partner/cricket-data";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("apiKey", "test-creds@2320")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
//            System.out.println(response.body());
            return response.body();  // Return the JSON response as String
        } else {
            throw new Exception("Failed to fetch data. Status code: " + response.statusCode());
        }
    }

    // Function to get the highest score in one innings with team name
    public static String getHighestScoreInnings(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray matches = jsonObject.getJSONArray("data");
        int highestScore = 0;
        String highestScoreTeam = "";

        for (int i = 0; i < matches.length(); i++) {
            JSONObject match = matches.getJSONObject(i);
            String t1s = match.getString("t1s");
            String t2s = match.getString("t2s");
            int t1Score = extractScore(t1s);
            int t2Score = extractScore(t2s);
            String t1 = match.getString("t1");
            String t2 = match.getString("t2");

            if (t1Score > highestScore) {
                highestScore = t1Score;
                highestScoreTeam = t1;
            }

            if (t2Score > highestScore) {
                highestScore = t2Score;
                highestScoreTeam = t2;
            }
        }

        return "Highest Score: " + highestScore + " by " + highestScoreTeam;
    }

    // Function to count the number of matches where both teams scored over 300 runs
    public static int countMatchesWith300PlusScores(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray matches = jsonObject.getJSONArray("data");
        int matchCount = 0;

        for (int i = 0; i < matches.length(); i++) {
            JSONObject match = matches.getJSONObject(i);
            String t1s = match.getString("t1s");
            String t2s = match.getString("t2s");
            int t1Score = extractScore(t1s);
            int t2Score = extractScore(t2s);

            if (t1Score > 300 && t2Score > 300) {
                matchCount++;
            }
        }

        return matchCount;
    }

    // Helper function to extract score from string "xxx/xx (ov)"
    private static int extractScore(String scoreStr) {
        try {
            return Integer.parseInt(scoreStr.split("/")[0]);
        } catch (Exception e) {
            return 0;
        }
    }
}
