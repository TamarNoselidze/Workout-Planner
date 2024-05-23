package com.workoutplanner.project;

import java.util.List;
import java.util.Scanner;


/**
 * The main class for the Workout Planner application.
 * Handles user registration, login, and requests for exercises and nutritional information.
 */
public class WorkoutPlanner {

    private static final DatabaseHelper dbHelper = new DatabaseHelper();
    private static final Scanner scanner = new Scanner(System.in);


    /**
     * The main method to start the Workout Planner application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {

        boolean exit = false;
        while (!exit) {

            System.out.println("Welcome to Workout Planner!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String option = scanner.next();
            scanner.nextLine();
            switch (option) {
                case "1": // handle registration
                    User currentUser = dbHelper.registerUser();
                    handleNewRequest(currentUser);
                    break;
                case "2":   // handle login
                    User loginedUser = dbHelper.loginUser();
                    if (loginedUser!=null){
                        showLoggedInMenu(loginedUser);
                    }
                    break;
                case "3":
                    exit = true;
                    System.out.println("\nThank you for using Workout Planner.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();

    }


    /**
     * Displays the logged-in user menu and handles user options.
     * @param user The current logged-in user.
     */
    private static void showLoggedInMenu(User user) {
        boolean exit = false;
        System.out.println("\nWelcome back, " + user.getUsername() +"!");

        while (!exit) {
            System.out.println("How can I help you today?");
            System.out.println("1. Access your existing, personalized data");
            System.out.println("2. Make a new request");
            System.out.println("3. Exit");

            String option = scanner.nextLine().trim();
            switch (option) {
                case "1": // Fetch and display the user's stored data
                    user.displayUserData();
                    System.out.println("\nIf you want to see details about a specific exercise, please, respond with the corresponding number.");
                    System.out.println("If not, please, respond with '0'");
                    while(true){
                        String response = scanner.nextLine().trim();
                        try{
                            int exNumber = Integer.parseInt(response);
                            if (exNumber >0 && exNumber<=user.numberOfExercises()) {
                                Exercise currExercise = user.getExercise(exNumber-1);
                                if (currExercise.getName() == null) { currExercise.displayWorkoutInfo();}
                                else{ currExercise.displayExerciseInfoGeneral(); }
                                System.out.println();
                                break;
                            }
                            else if (exNumber == 0){
                                break;
                            }
                            else {
                                System.out.println("Not a valid response, please try again.");
                            }
                        }catch (NumberFormatException e){
                            System.out.println("Not a valid response, please try again.");
                        }
                    }

                    if (user.hasNutritionalInfo()){
                        System.out.println("You also have a detailed nutritional information saved. Would you like to display it? ");
                        System.out.println(" 1. Yes \n 2. No");
                        String response = scanner.nextLine().trim();
                        while(!response.equals("1") && !response.equals("2")){
                            System.out.println("Invalid response. Please choose 1 or 2.");
                        }
                        if (response.equals("1")){
                            NutritionalInfo info = user.getNutritionalInfo();
                            info.displayNutritionalInfo();
                        }
                    }

                    System.out.println("\nOkay, now redirecting back to the main menu.");
                    break;
                case "2":  // Make a new request
                    handleNewRequest(user);
                    break;
                case "3":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please, try again.");
                    break;
            }
        }
    }


    /**
     * Handles new requests from the user.
     * @param currentUser The current logged-in user.
     */
    private static void handleNewRequest(User currentUser) {

        boolean done = false;
        String requestType = "";

        System.out.println("What type of request would you like to make?");

        while (!done){
            System.out.println("1. Fetch exercises");
            System.out.println("2. Create workout routine");
            System.out.println("3. Get nutritional information");
            System.out.println("4. Return");

            requestType = scanner.nextLine().trim();

            switch (requestType) {
                case "1":
                    String exerciseType = "1";
                    handleFetching(exerciseType, currentUser);
                    break;
                case "2":
                    exerciseType = "2";
                    handleFetching(exerciseType, currentUser);
                    break;
                case "3":
                    getNutritionalInfo(currentUser);
                    break;
                case "4":
                    done = true;
                    break;
                default:
                    System.out.println("Invalid option. Please, try again.");
                    break;
            }

        }
    }


    /**
     * Handles fetching exercises or workout routines based on user input.
     * @param exerciseType The type of exercise to fetch.
     * @param currentUser The current logged-in user.
     */
    private static void handleFetching(String exerciseType, User currentUser) {
        if (exerciseType.equals("1")){
            System.out.println("What body part would you like to fetch exercises for?");
            String targetMuscle = scanner.nextLine().trim().toLowerCase();
            String mappedBodyPart = Exercise.getMappedBodyPart(targetMuscle);
            while (true) {
                if (mappedBodyPart == null){
                    System.out.println("We don't have exercises for this body part. Would you maybe like to try 'upper arms' or 'lower legs'?\nPlease, enter another body part name: ");
                    targetMuscle = scanner.nextLine().trim().toLowerCase();
                    mappedBodyPart = Exercise.getMappedBodyPart(targetMuscle);
                }
                else {
                    List<Exercise> exercises = ExerciseDB.fetchExercises(targetMuscle, mappedBodyPart);
                    if (!exercises.isEmpty()) {
                        displayFoundExercises(exercises, currentUser, false);
                        break;
                    }
                }
            }
        }
        else { // type == 2
            System.out.println("What specific muscle would you like to fetch exercises for?");
            String targetMuscle = scanner.nextLine().trim().toLowerCase();
            while (!Exercise.isTargetValid(targetMuscle)){
                System.out.println("Could not find information for the muscle '" + targetMuscle + "'.");
                Exercise.displayMuscleList();
                targetMuscle = scanner.nextLine().trim().toLowerCase();
            }
            System.out.println("Noted the target muscle: " + targetMuscle);

            System.out.println("\nWould you like to specify equipment? (Press enter to skip)");
            String equipment = scanner.nextLine();
            if (!equipment.isEmpty()){
                while (!Exercise.isEquipmentValid(equipment)){
                    System.out.println("Could not find the equipment '" + equipment + "'.");
                    Exercise.displayEquipmentList();
                    equipment = scanner.nextLine().trim().toLowerCase();
                }
                System.out.println("Noted.");
            }

            System.out.println("\nWould you like to specify the intensity level? \nYou can choose from the options below or press enter to skip.");
            System.out.println("\n  1. Beginner\n  2. Intermediate\n  3. Expert");
            String intensityOption = scanner.nextLine();
            String intensityLevel = "";
            if (!intensityOption.isEmpty()){
                while (!intensityOption.equals("1") && !intensityOption.equals("2") && !intensityOption.equals("3")){
                    System.out.println("Invalid option: the value should an integer corresponding to one of the options. Please, try again.");
                    intensityOption = scanner.nextLine();
                }
                if (intensityOption.equals("1")){ intensityLevel = "beginner"; }
                else if (intensityOption.equals("2")) { intensityLevel = "intermediate"; }
                else { intensityLevel = "expert"; }
            }

            List<Exercise> exercises = WorkoutDB.fetchWorkouts(targetMuscle, equipment, intensityLevel);
            if (!exercises.isEmpty()) {
                displayFoundExercises(exercises, currentUser, true);
            }
            else {
                System.out.println("Could not find any exercise with these specifications.\n");
            }

        }
    }


    /**
     * Displays the list of found exercises and handles user interaction for saving exercises.
     * @param exercises The list of exercises to display.
     * @param currentUser The current logged-in user.
     * @param workout Indicates if the exercises are workout routines.
     */
    static void displayFoundExercises(List<Exercise> exercises, User currentUser, boolean workout){
        System.out.println(exercises.size() + " exercises found:");
        for (int i=0; i<exercises.size(); i++) {
            Exercise ex = exercises.get(i);
            if (workout){ System.out.println((i+1) + ". " + ex.getWorkout()); }
            else { System.out.println((i+1) + ". " + ex.getName()); }
        }

        System.out.println("\nWould you like to get further information about any of these exercises?");
        System.out.println(" - If yes, please choose a corresponding number, otherwise reply with '0'.");

        while (true) {
            String response = scanner.nextLine().trim();

            if (response.matches("\\d+")) {
                int number = Integer.parseInt(response);
                if (number == 0) {
                    System.out.println("Okay, going back to the main menu.");
                    break;
                } else if (number > 0 && number <= exercises.size()) {
                    Exercise currExercise = exercises.get(number - 1);
                    if (workout){ currExercise.displayWorkoutInfo(); }
                    else{ currExercise.displayExerciseInfoGeneral(); }
                    System.out.println();

                    saveExerciseMenu(currentUser, currExercise, workout);
                    System.out.println("\nOkay, going back to the main menu.");
                    break;
                }
                else {
                    System.out.println("Invalid option. Please, choose an integer in the correct range.");
                }
            }
        }
    }


    /**
     * Handles saving exercises to the user's account.
     * @param currentUser The current logged-in user.
     * @param currExercise The exercise to save.
     * @param workout Indicates if the exercise is a workout routine.
     */
    static void saveExerciseMenu(User currentUser, Exercise currExercise, boolean workout){
        System.out.println("Would you like to save this exercise?");
        System.out.println(" 1. Yes");
        System.out.println(" 2. No");
        String response = scanner.nextLine().trim();
        while (!response.equals("1") && !response.equals("2")) {
            System.out.println("Invalid number. Please, choose from the two options: either '1' or '2'. ");
            response = scanner.nextLine().trim();
        }
        if (response.equals("1")){
            currentUser.saveExercise(currExercise, workout);
            DatabaseHelper.updateUserInfo(currentUser); // Save the updated user data
        }

    }


    /**
     * Fetches and displays nutritional information based on user input.
     * @param currUser The current logged-in user.
     */
    private static void getNutritionalInfo(User currUser) {
        System.out.println("Enter measurement units: 'std' for standard, 'met' for metric: ");
        String measurementUnits = scanner.nextLine().trim();
        while(!measurementUnits.equals("std") && !measurementUnits.equals("met")){
            System.out.println("Invalid option, please try again.");
            measurementUnits = scanner.nextLine().trim();
        }

        System.out.print("Enter sex (male/female): ");
        String sex = scanner.nextLine().trim();
        while(!sex.equals("male") && !sex.equals("female")){
            System.out.println("Invalid option, please try again.");
            sex = scanner.nextLine().trim();
        }

        System.out.print("Enter age: ");
        int ageValue;
        while(true){
            try{ ageValue = scanner.nextInt(); break;}
            catch (Exception e){
                System.out.println("Age should be an integer. Try again.");
                scanner.nextLine();
            }
        }

        int feet = 0, inches = 0, lbs = 0, cm = 0, kilos = 0;

        if (measurementUnits.equals("std")){
            while (true){
                try{
                    System.out.print("Enter height in feet: ");
                    feet = scanner.nextInt();

                    System.out.print("Enter height in inches: ");
                    inches = scanner.nextInt();

                    System.out.print("Enter weight in lbs: ");
                    lbs = scanner.nextInt();
                    break;
                }
                catch (Exception e){
                    System.out.println("Something went wrong. Measurement units should have integer values. Please, try again.");
                }
            }

        }
        else{   // "met"
            while (true){
                try {
                    scanner.nextLine();
                    System.out.print("Enter height in cm: ");
                    cm = scanner.nextInt();
                    System.out.print("Enter weight in kilos: ");
                    kilos = scanner.nextInt();
                    break;
                }
                catch (Exception e){
                    System.out.println("Something went wrong. Measurement units should have integer values. Please, try again.");
                }
            }

        }

        scanner.nextLine();
        System.out.println("Choose activity level from the options below: ");
        System.out.println("  1. Inactive \n  2. Low Active \n  3. Active \n  4. Very Active");
        String activityOption = scanner.nextLine().trim();
        while (!activityOption.equals("1") && !activityOption.equals("2") && !activityOption.equals("3") && !activityOption.equals("4")){
            System.out.println("Invalid option. Try again.");
            activityOption = scanner.nextLine().trim();
        }
        String activityLevel = "";
        if (activityOption.equals("1")) { activityLevel = "Inactive"; }
        else if (activityOption.equals("2")) { activityLevel = "Low%20Active"; }
        else if (activityOption.equals("3")) { activityLevel = "Active"; }
        else { activityLevel = "Very%20Active"; }


        NutritionalInfo nutritionalInfo = NutritionalDB.fetchNutritionalInfo(measurementUnits, sex, ageValue, feet, inches, lbs, cm, kilos, activityLevel);
        if (nutritionalInfo != null) {
            nutritionalInfo.displayNutritionalInfo();
            System.out.println("\nWould you like to save this information to your account? (yes/no)");
            String response = scanner.nextLine().trim().toLowerCase();
            while(!response.equals("yes") && !response.equals("no")){
                System.out.println("Invalid option. Please choose 'yes' or 'no'");
                response = scanner.nextLine().trim().toLowerCase();
            }
            if (response.equals("yes")){
                currUser.saveNutritionalInfo(nutritionalInfo);
                DatabaseHelper.updateUserInfo(currUser); // Save the updated user data
            }
        } else {
            System.out.println("Failed to fetch nutritional info.");
        }
    }

}