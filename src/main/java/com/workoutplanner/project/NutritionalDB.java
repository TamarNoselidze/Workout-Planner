package com.workoutplanner.project;

import com.google.gson.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**
 * The NutritionalDB class handles fetching nutritional information
 * from the Nutrition Calculator API.
 */
public class NutritionalDB {
    private static final String API_HOST = "nutrition-calculator.p.rapidapi.com";
    private static final String API_URL = "https://nutrition-calculator.p.rapidapi.com/api/nutrition-info";
    private static final String API_KEY = "3345f16076msh89caec5bb418fc8p14bc11jsnbcff5c60c28f";

    /**
     * Fetches nutritional information based on the provided parameters.
     *
     * @param measurementUnits The measurement units, either "std" or "met".
     * @param sex The sex of the user, either "male" or "female".
     * @param ageValue The age of the user (in years).
     * @param feet The height in feet (used if measurementUnits is "std").
     * @param inches The height in inches (used if measurementUnits is "std").
     * @param lbs The weight in pounds (used if measurementUnits is "std").
     * @param cm The height in centimeters (used if measurementUnits is "met").
     * @param kilos The weight in kilograms (used if measurementUnits is "met").
     * @param activityLevel The activity level of the user.
     * @return A NutritionalInfo object containing the fetched nutritional information.
     */
    public static NutritionalInfo fetchNutritionalInfo(String measurementUnits, String sex, int ageValue, int feet, int inches, int lbs, int cm, int kilos, String activityLevel) {
        NutritionalInfo nutritionalInfo = new NutritionalInfo();
        String queryUrl = API_URL + "?measurement_units=" + measurementUnits + "&sex=" + sex + "&age_value=" + ageValue + "&age_type=yrs";
        if (feet != 0){
            queryUrl += "&feet=" + feet + "&inches=" + inches + "&lbs=" + lbs + "&activity_level=" + activityLevel;
        }
        else {
            queryUrl += "&cm=" + cm + "&kilos=" + kilos + "&activity_level=" + activityLevel;
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
                JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
                reader.close();

                nutritionalInfo.setMeasurementUnits(measurementUnits);
                nutritionalInfo.setSex(sex);
                nutritionalInfo.setAgeValue(ageValue);

                if (feet!=0){
                    nutritionalInfo.setFeet(feet);
                    nutritionalInfo.setInches(inches);
                    nutritionalInfo.setLbs(lbs);
                }
                else{
                    nutritionalInfo.setCm(cm);
                    nutritionalInfo.setKilos(kilos);
                }

                nutritionalInfo.setActivityLevel(activityLevel);
                nutritionalInfo.setBmi(jsonObject.getAsJsonObject("BMI_EER").get("BMI").getAsString());
                nutritionalInfo.setEstimatedCaloricNeeds(jsonObject.getAsJsonObject("BMI_EER").get("Estimated Daily Caloric Needs").getAsString());
                // Parse macronutrients_table
                JsonArray macronutrients = jsonObject.getAsJsonObject("macronutrients_table").getAsJsonArray("macronutrients-table");
                nutritionalInfo.setMacronutrientsTable(new Gson().fromJson(macronutrients, List.class));
                // Parse essential-minerals-table
                JsonArray essentialMinerals = jsonObject.getAsJsonObject("minerals_table").getAsJsonArray("essential-minerals-table");
                nutritionalInfo.setEssentialMineralsTable(new Gson().fromJson(essentialMinerals, List.class));
                // Parse non-essential-minerals-table
                JsonArray nonEssentialMinerals = jsonObject.getAsJsonObject("non_essential_minerals_table").getAsJsonArray("non-essential-minerals-table");
                nutritionalInfo.setNonEssentialMineralsTable(new Gson().fromJson(nonEssentialMinerals, List.class));
                // Parse vitamins-table
                JsonArray vitamins = jsonObject.getAsJsonObject("vitamins_table").getAsJsonArray("vitamins-table");
                nutritionalInfo.setVitaminsTable(new Gson().fromJson(vitamins, List.class));

            } else {
                System.out.println("GET request not worked");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nutritionalInfo;
    }
}
