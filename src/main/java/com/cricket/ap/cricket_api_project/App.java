package com.cricket.ap.cricket_api_project;

public class App 
{
    public static void main( String[] args )
    {
        try {
            // Fetch data from the API
            String cricketData = ConsumeApi.fetchCricketData();
            
//             Get and print the highest score in one innings
            String highestScoreResult = ConsumeApi.getHighestScoreInnings(cricketData);
            System.out.println(highestScoreResult);
            
            // Get and print the number of matches where both teams scored over 300
            int matchesWith300Plus = ConsumeApi.countMatchesWith300PlusScores(cricketData);
            System.out.println("Number of matches with both teams scoring over 300: " + matchesWith300Plus);
//            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
