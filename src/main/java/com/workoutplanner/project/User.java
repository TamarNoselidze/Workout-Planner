package com.workoutplanner.project;

import java.util.List;
import java.util.ArrayList;


/**
 * This class represents a user in the Workout Planner application.
 * A user has a username, password, a list of saved exercises, and nutritional information.
 */
public class User {
    private String username;
    private String password;
    private List<Exercise> savedExercises;
    private NutritionalInfo nutritionalInfo;

    /**
     * Constructs a new User with the specified username and password.
     * Initializes the list of saved exercises and sets nutritional info to null.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password; // In real applications, store hashed passwords, not plain text
        this.savedExercises = new ArrayList<>();
        this.nutritionalInfo = null;
    }

    // Getters and Setters
    public String getPassword(){return  password;}
    public  String getUsername(){return  username;}


    /**
     * Saves an exercise to the user's list of saved exercises.
     * @param exercise The exercise to be saved.
     * @param workout A boolean indicating if the exercise is a workout.
     */
    public void saveExercise(Exercise exercise, boolean workout) {
        savedExercises.add(exercise);
        if (workout) { System.out.println("Exercise '" + exercise.getWorkout() + "' saved to the database!" ); }
        else{ System.out.println("Exercise '" + exercise.getName() + "' saved to the database!" ); }
    }


    /**
     * Displays the list of exercises saved by the user.
     */
    public void displayUserData(){
        System.out.println("Here is a list of your saved exercises: ");
        for (int i=0; i<savedExercises.size(); i++){
            Exercise ex = savedExercises.get(i);
            if (ex.getName() == null){ System.out.println("  " + (i+1) +") " + ex.getWorkout()); }
            else { System.out.println("  " + (i+1) +") " + ex.getName()); }
        }
    }


    /**
     * Returns the number of exercises saved by the user.
     * @return The number of saved exercises.
     */
    public int numberOfExercises(){
        return savedExercises.size();
    }


    /**
     * Returns the exercise at the specified index.
     * @param index The index of the exercise to return.
     * @return The exercise at the specified index.
     */
    public Exercise getExercise(int index){
        return savedExercises.get(index);
    }


    /**
     * Saves the user's nutritional information.
     * @param info The nutritional information to be saved.
     */
    public void saveNutritionalInfo(NutritionalInfo info){
        this.nutritionalInfo = info;
    }


    /**
     * Checks if the user has nutritional information saved.
     * @return True if nutritional information is saved, false otherwise.
     */
    public boolean hasNutritionalInfo(){
        return this.nutritionalInfo != null;
    }


    /**
     * Returns the user's nutritional information.
     * @return The user's nutritional information.
     */
    public NutritionalInfo getNutritionalInfo() {
        return nutritionalInfo;
    }
}
