package com.workoutplanner.project;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The ExerciseDB class handles fetching exercises from the ExerciseDB API.
 */
public class ExerciseDB {

    private static final String API_HOST = "exercisedb.p.rapidapi.com";
    private static final String API_KEY = "3345f16076msh89caec5bb418fc8p14bc11jsnbcff5c60c28f";
    private static final String API_URL = "https://exercisedb.p.rapidapi.com"; // Base URL for the ExerciseDB API


    /**
     * Fetches exercises based on the provided target and mapped target muscle groups.
     *
     * @param target The target muscle group specified by the user.
     * @param mappedTarget The mapped target muscle group for API query.
     * @return A list of Exercise objects containing the fetched exercises.
     */
    public static List<Exercise> fetchExercises(String target, String mappedTarget) {
        List<Exercise> exercises = new ArrayList<>();
        List<Exercise> filteredExercises = new ArrayList<>();
        String queryUrl = API_URL + "/exercises/bodyPart/" + mappedTarget; // Adjust if using different endpoint

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
//                    System.out.println(exercise.getName());
//                    System.out.println(exercise.getInstructions());
                    exercises.add(exercise);
                }

                if (target.equals(mappedTarget)){
                    filteredExercises = exercises;
                }
                else {
                    filteredExercises = exercises.stream()
                            .filter(ex -> ex.getTarget().toLowerCase().contains(target))
                            .collect(Collectors.toList());
                }
            }
            else {
                System.out.println("GET request not worked");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filteredExercises;
    }

}
