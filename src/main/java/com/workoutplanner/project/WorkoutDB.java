package com.workoutplanner.project;

import com.google.gson.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The WorkoutDB class handles fetching workout exercises
 * from the Workout API.
 */
public class WorkoutDB {

    private static final String API_HOST = "work-out-api1.p.rapidapi.com";
    private static final String API_URL = "https://work-out-api1.p.rapidapi.com/search";
    private static final String API_KEY = "3345f16076msh89caec5bb418fc8p14bc11jsnbcff5c60c28f";

    /**
     * Fetches workout exercises based on the provided parameters.
     * @param muscles The target muscle group.
     * @param equipment The equipment used.
     * @param intensityLevel The intensity level of the workout.
     * @return A list of Exercise objects containing the fetched workouts.
     */
    public static List<Exercise> fetchWorkouts(String muscles, String equipment, String intensityLevel) {
        List<Exercise> exercises = new ArrayList<>();
        String queryUrl = API_URL + "?Muscles=" + muscles;
        if (!equipment.isEmpty()){
            queryUrl += "&Equipment=" + equipment;
        }
        if (!intensityLevel.isEmpty()){
            queryUrl += "&Intensity_Level=" + intensityLevel;
        }

        try {
            URL url = new URL(queryUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-RapidAPI-Key", API_KEY);
            connection.setRequestProperty("X-RapidAPI-Host", API_HOST);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
                reader.close();

                Gson gson = new Gson();
                for (JsonElement element : jsonArray) {
                    Exercise exercise = gson.fromJson(element, Exercise.class);
                    exercises.add(exercise);
                }
            } else {
                System.out.println("GET request not worked");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exercises;
    }
}
