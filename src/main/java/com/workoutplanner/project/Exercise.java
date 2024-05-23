package com.workoutplanner.project;

import java.util.*;
import com.google.gson.annotations.SerializedName;


/**
 * This class represents an exercise with various attributes and methods to display exercise information.
 */
public class Exercise {
    private String id;
    private String name;
    private String bodyPart;
    private String equipment;
    private String target;
    private String gifUrl;
    private List<String> secondaryMuscles;
    private List<String> instructions;

    @SerializedName("Muscles")
    private String muscle;

    @SerializedName("WorkOut")
    private String workout;

    @SerializedName("Intensity_Level")
    private String intensityLevel;

    @SerializedName("Beginner Sets")
    private String beginnerSets;

    @SerializedName("Intermediate Sets")
    private String intermediateSets;

    @SerializedName("Expert Sets")
    private String expertSets;

    @SerializedName("Explaination")
    private String explanation;

    @SerializedName("Long Explanation")
    private String longExplanation;

    @SerializedName("Video")
    private String video;


    private static List<String> muscleList = new ArrayList<>(List.of(new String[]{"biceps", "triceps", "chest", "abs", "back", "legs", "lats", "hamstring", "calves", "quadriceps", "trapezius", "shoulders", "glutes"}));
    private static List<String> equipmentList = new ArrayList<>(List.of(new String[]{"barbell", "dumbbells", "kettlebell", "bench", "incline bench", "decline bench", "cable machine", "resistance band", "bicep curl machine", "leg press" }));


    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBodyPart() { return bodyPart; }
    public void setBodyPart(String bodyPart) { this.bodyPart = bodyPart; }

    public String getEquipment() { return equipment; }
    public void setEquipment(String equipment) { this.equipment = equipment; }

    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }

    public String getGifUrl() { return gifUrl; }
    public void setGifUrl(String gifUrl) { this.gifUrl = gifUrl; }

    public List<String> getSecondaryMuscles() { return secondaryMuscles; }
    public void setSecondaryMuscles(List<String> secondaryMuscles) { this.secondaryMuscles = secondaryMuscles; }

    private static final Map<String, String> bodyPartMap = new HashMap<>();

    static {
        bodyPartMap.put("back", "back");
        bodyPartMap.put("cardio", "cardio");
        bodyPartMap.put("chest", "chest");
        bodyPartMap.put("lower arms", "lower arms");
        bodyPartMap.put("lower legs", "lower legs");
        bodyPartMap.put("neck", "neck");
        bodyPartMap.put("shoulders", "shoulders");
        bodyPartMap.put("upper arms", "upper arms");
        bodyPartMap.put("upper legs", "upper legs");
        bodyPartMap.put("waist", "waist");
        // Mappings for common names to API categories
        bodyPartMap.put("biceps", "upper arms");
        bodyPartMap.put("triceps", "upper arms");
        bodyPartMap.put("quads", "upper legs");
        bodyPartMap.put("hamstrings", "upper legs");
        bodyPartMap.put("glutes", "upper legs");
        bodyPartMap.put("calves", "lower legs");
        bodyPartMap.put("shins", "lower legs");
        bodyPartMap.put("abs", "waist");
    }

    public String getInstructions() {
        String result_string = "";
        for (String x : instructions) {
            result_string += x+"\n";
        }
        return result_string;
    }

    public void setInstructions(List<String> instructions) { this.instructions = instructions; }

    public String getMuscles() { return muscle; }
    public void setMuscles(String muscles) { this.muscle = muscles; }

    public String getWorkout() { return workout; }
    public void setWorkout(String workout) { this.workout = workout; }

    public String getIntensityLevel() { return intensityLevel; }
    public void setIntensityLevel(String intensityLevel) { this.intensityLevel = intensityLevel; }

    public String getBeginnerSets() { return beginnerSets; }
    public void setBeginnerSets(String beginnerSets) { this.beginnerSets = beginnerSets; }

    public String getIntermediateSets() { return intermediateSets; }
    public void setIntermediateSets(String intermediateSets) { this.intermediateSets = intermediateSets; }

    public String getExpertSets() { return expertSets; }
    public void setExpertSets(String expertSets) { this.expertSets = expertSets; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public String getLongExplanation() { return longExplanation; }
    public void setLongExplanation(String longExplanation) { this.longExplanation = longExplanation; }

    public String getVideo() { return video; }
    public void setVideo(String video) { this.video = video; }


    /**
     * Displays general information about the exercise.
     */
    public void displayExerciseInfoGeneral(){
        System.out.println("\nInformation about >" + name + "<:");
        System.out.println("-----------------------------------------------------------");
        System.out.println("This exercise mainly targets " + bodyPart + ".");
        System.out.print(" - Also, the secondary muscles are: ");
        for (int i=0; i<secondaryMuscles.size(); i++){
            if (i==secondaryMuscles.size()-1) {
                System.out.println("and " + secondaryMuscles.get(i) + ".");
            }else {
                System.out.print(secondaryMuscles.get(i) + ", ");
            }
        }

        System.out.println("\nNecessary equipment: " + equipment + ".");
        System.out.println("\nInstructions: ");
        for(int i=0; i<instructions.size(); i++){
            System.out.println("  * " + instructions.get(i));
        }

        System.out.println("\nFollow the link to see a visual demonstration of the exercise: ");
        System.out.println(gifUrl);
        System.out.println("-----------------------------------------------------------");
    }


    /**
     * Displays detailed information about the workout.
     */
    public void displayWorkoutInfo(){
        System.out.println("\nInformation about >" + workout + "<:");
        System.out.println("-----------------------------------------------------------");
        System.out.println("This exercise mainly targets " + muscle + ".");

        if (intensityLevel.isEmpty()){
            System.out.println("- Set/Rep range for beginners: " + beginnerSets);
            System.out.println("- Set/Rep range for intermediate level: " + intermediateSets);
            System.out.println("- Set/Rep range for expert level: " + expertSets);
        }
        else{
            if (intensityLevel.equals("beginner")){ System.out.println("- Set/Rep range: " + beginnerSets); }
            else if (intensityLevel.equals("intermediate")) { System.out.println("- Set/Rep range: " + intermediateSets); }
            else { System.out.println("- Set/Rep range: " + expertSets); }
        }

        System.out.println("\nInstructions: ");
        System.out.println("  > The equipment you will need for this workout: " + equipment);

        String instructionsSource = (longExplanation != null && !longExplanation.isEmpty()) ? longExplanation : explanation;
        if (instructionsSource != null && !instructionsSource.isEmpty()) {
            List<String> instructionsList = Arrays.stream(instructionsSource.split("(?<=\\.)"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
            for (String instruction : instructionsList) {
                System.out.println("  * " + instruction);
            }
        } else {
            System.out.println("  * No detailed instructions available.");
        }


        System.out.println("\nFollow the link to see a video on YouTube demonstrating the exercise: ");
        System.out.println(video);
        System.out.println("-----------------------------------------------------------");
    }

    /**
     * Displays a list of available target muscles.
     */
    public static void displayMuscleList(){
        System.out.println("\nPlease, choose a target muscle from the list below: ");
        for (String muscle : muscleList){
            System.out.println(" * " + muscle + ";");
        }

        System.out.println("You can also request for 'stretching' or 'warm-up' exercises. \n");
    }


    /**
     * Displays a list of available equipment.
     */
    public static void displayEquipmentList(){
        System.out.println("\n Please, choose an equipment from the list below: ");
        for (String eq : equipmentList){
            System.out.println(" * " + eq + ";");
        }
        System.out.println("   . . .\n");
    }


    /**
     * Checks if the provided target muscle is valid.
     * @param targetMuscle The target muscle to check.
     * @return true if the target muscle is valid, false otherwise.
     */
    public static boolean isTargetValid(String targetMuscle){
        return muscleList.contains(targetMuscle);
    }

    /**
     * Checks if the provided equipment is valid.
     * @param equipment The equipment to check.
     * @return true if the equipment is valid, false otherwise.
     */
    public static boolean isEquipmentValid(String equipment){
        return equipmentList.contains(equipment);
    }


    /**
     * Maps a user-provided body part to the corresponding API category.
     * @param userBodyPart The body part provided by the user.
     * @return The corresponding API category, or null if no mapping exists.
     */
    public static String getMappedBodyPart(String userBodyPart) {
        return bodyPartMap.getOrDefault(userBodyPart.toLowerCase(), null);
    }

}
