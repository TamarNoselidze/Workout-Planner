# Introduction

The Workout Planner is a Java-based console application designed to help users create, customize, and manage their workout routines and nutritional information. 
It features user authentication, allowing users to create accounts and log in to personalize their fitness plans. 
The application integrates with external APIs to fetch general and tailored exercises based on muscle groups, equipment, and intensity levels. 
Users can also retrieve and save detailed nutritional information, including BMI, caloric needs, and macronutrient recommendations. 
The program stores user data locally in a JSON file, ensuring personalized and persistent user experiences. 
It provides an intuitive interface for viewing saved exercises and nutritional information, making it a comprehensive tool for fitness enthusiasts. 
Future enhancements may include a graphical user interface and additional features for tracking progress and goals.



# Features

- User Authentication: Users can create accounts or log in with existing credentials to personalize their workout routines.
- Exercise Database: A local database of exercises, including exercise name, muscle groups targeted and instructions.
- Workout Routine Creation: Users can design their workout routines by selecting exercises, specifying sets, reps, and rest intervals.
- Workout Suggestions: The system provides workout suggestions based on users' fitness goals.
- Nutritional Information: Users can fetch and save detailed nutritional information, including BMI, caloric needs, macronutrients, minerals, and vitamins.


# Requirements

- Java Development Kit (JDK) 8 or higher
- Internet connection for API access


# Setup and Installation
1. Clone the Repository:

```
git clone https://github.com/TamarNoselidze/Workout-Planner.git
cd workout-planner
```

2. Download Dependencies:
Ensure you have all required libraries. This project uses Gson for JSON parsing

3. Set Up the Database:
Create a `database.json` file in the root directory of the project. This file will store user data.
(a sample one is provided, feel free to overwrite)

4. Compile the Code:
Navigate to the `src` directory and compile the Java files:
```
javac -d ../bin com/workoutplanner/project/*.java
```


# Execution

Navigate to the `bin` directory and run the WorkoutPlanner class:
```
java com.workoutplanner.project.WorkoutPlanner
```

# Usage

__Main Menu__ 

Register: Create a new account by entering a username and password.
Login: Log in with an existing account by entering your username and password.
Exit: Exit the application.

__Logged In Menu__

Access Existing Data: View your saved exercises and nutritional information.
Make a New Request: Fetch new exercises or nutritional information.
Exit: Log out and return to the main menu.

__Fetch Exercises__

1. General Exercises: Fetch exercises based on the target muscle.
2. Tailored Exercises: Fetch exercises based on specific muscle, equipment, and intensity level.

__Get Nutritional Information__



# API Integration
The application integrates with the following APIs:

* ExerciseDB API: Fetches general exercises based on the target muscle.
* WorkoutDB API: Fetches tailored exercises based on specific criteria.
* Nutrition Calculator API: Fetches detailed nutritional information.



