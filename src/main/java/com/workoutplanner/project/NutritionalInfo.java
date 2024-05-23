package com.workoutplanner.project;

import java.util.List;

/**
 * The NutritionalInfo class holds the nutritional information data.
 */
public class NutritionalInfo {
    private String measurementUnits;
    private String sex;
    private int ageValue;
    private String ageType = "yrs";
    private int feet;
    private int inches;
    private int lbs;
    private int cm;
    private int kilos;
    private String activityLevel;
    private String bmi;
    private String estimatedCaloricNeeds;
    private List<List<String>> macronutrientsTable;
    private List<List<String>> essentialMineralsTable;
    private List<List<String>> nonEssentialMineralsTable;
    private List<List<String>> vitaminsTable;


    // Getters and Setters
    public String getMeasurementUnits() { return measurementUnits; }
    public void setMeasurementUnits(String measurementUnits) { this.measurementUnits = measurementUnits; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public int getAgeValue() { return ageValue; }
    public void setAgeValue(int ageValue) { this.ageValue = ageValue; }

    public int getFeet() { return feet; }
    public void setFeet(int feet) { this.feet = feet; }

    public int getInches() { return inches; }
    public void setInches(int inches) { this.inches = inches; }

    public int getLbs() { return lbs; }
    public void setLbs(int lbs) { this.lbs = lbs; }

    public int getCm() { return cm; }
    public void setCm(int cm) { this.cm = cm; }

    public int getKilos() { return kilos; }
    public void setKilos(int kilos) { this.kilos = kilos; }

    public String getActivityLevel() { return activityLevel; }
    public void setActivityLevel(String activityLevel) { this.activityLevel = activityLevel; }

    public String getBmi() { return bmi; }
    public void setBmi(String bmi) { this.bmi = bmi; }

    public String getEstimatedCaloricNeeds() { return estimatedCaloricNeeds; }
    public void setEstimatedCaloricNeeds(String estimatedCaloricNeeds) { this.estimatedCaloricNeeds = estimatedCaloricNeeds; }

    public List<List<String>> getMacronutrientsTable() { return macronutrientsTable; }
    public void setMacronutrientsTable(List<List<String>> macronutrientsTable) { this.macronutrientsTable = macronutrientsTable; }

    public List<List<String>> getEssentialMineralsTable() { return essentialMineralsTable; }
    public void setEssentialMineralsTable(List<List<String>> essentialMineralsTable) { this.essentialMineralsTable = essentialMineralsTable; }

    public List<List<String>> getNonEssentialMineralsTable() { return nonEssentialMineralsTable; }
    public void setNonEssentialMineralsTable(List<List<String>> nonEssentialMineralsTable) { this.nonEssentialMineralsTable = nonEssentialMineralsTable; }

    public List<List<String>> getVitaminsTable() { return vitaminsTable; }
    public void setVitaminsTable(List<List<String>> vitaminsTable) { this.vitaminsTable = vitaminsTable; }


    /**
     * Displays the nutritional information retrieved.
     */
    public void displayNutritionalInfo() {
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Nutritional Information:");
        System.out.println(" - BMI: " + bmi);
        System.out.println(" - Estimated Daily Caloric Needs: " + estimatedCaloricNeeds);
        System.out.println("\nMacronutrients:");
        for (List<String> entry : macronutrientsTable) {
            System.out.println("  " + entry.get(0) + ": " + entry.get(1));
        }
        System.out.println("\nEssential Minerals:");
        for (List<String> entry : essentialMineralsTable) {
            System.out.println("  " + entry.get(0) + ": " + entry.get(1) + " (UL: " + entry.get(2) + ")");
        }
        System.out.println("\nNon-Essential Minerals:");
        for (List<String> entry : nonEssentialMineralsTable) {
            System.out.println("  " + entry.get(0) + ": " + entry.get(1) + " (UL: " + entry.get(2) + ")");
        }
        System.out.println("\nVitamins:");
        for (List<String> entry : vitaminsTable) {
            System.out.println("  " + entry.get(0) + ": " + entry.get(1) + " (UL: " + entry.get(2) + ")");
        }
        System.out.println("----------------------------------------------------------------------");
    }

}
