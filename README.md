# NIT3213 Mobile Application Development – Assignment 2

## Student Information

- **Student Name:** MD PRANTOR MRIDHA
- **Student ID:** 8161372
- **Unit:** NIT3213 Mobile Application Development
- **Assessment:** Assignment 2

## Project Overview

This Android application was developed for NIT3213 Assignment 2 using Kotlin and Android Studio.

The application communicates with the provided API and allows a user to log in using their Student ID and first name. After successful authentication, the application retrieves dashboard data from the API and displays a list of entities. Users can select an entity to view its detailed information.

## Features

- User login using Student ID and first name
- API communication using Retrofit
- Dashboard displaying entities retrieved from the API
- Detailed entity information screen
- RecyclerView for displaying dashboard data
- MVVM architecture
- Repository pattern for data access
- Hilt for dependency injection
- ViewBinding for UI interaction
- Navigation Component for screen navigation
- Loading and error handling
- Unit testing using JUnit, MockK and Kotlin Coroutines Test

## Application Architecture

The project follows the **MVVM (Model-View-ViewModel)** architecture.

- **Model:** Contains API request and response data classes.
- **Repository:** Handles communication between the API service and ViewModels.
- **ViewModel:** Manages UI-related data and application logic.
- **View:** Activities and Fragments display data to the user.

Dependency injection is implemented using **Hilt**.

## Screens

The application contains three main screens:

1. **Login Screen** – Allows the user to enter their Student ID and first name.
2. **Dashboard Screen** – Displays entities returned by the API.
3. **Details Screen** – Displays detailed information about a selected entity.

## Testing

Unit tests have been implemented for the ViewModels.

### LoginViewModelTest

Tests include:

- Empty login validation
- Successful login and keypass response
- Failed login error handling

### DashboardViewModelTest

Tests include:

- Empty keypass validation
- Successful dashboard data retrieval
- Failed dashboard request error handling

A total of **6 ViewModel unit tests** are included in the project.

## Technologies Used

- Kotlin
- Android Studio
- Android SDK
- Retrofit
- Hilt
- ViewModel and LiveData
- RecyclerView
- Navigation Component
- ViewBinding
- Kotlin Coroutines
- JUnit
- MockK
- Git

## How to Run the Application

1. Clone or download the project.
2. Open the project in Android Studio.
3. Allow Gradle to sync and download the required dependencies.
4. Start an Android emulator or connect an Android device.
5. Run the `app` configuration.
6. Enter the required Student ID and first name on the login screen.
7. After successful authentication, the dashboard will display the retrieved entities.
8. Select an entity to view its details.

## Unit Tests

To run the unit tests in Android Studio:

1. Navigate to `app/src/test/java/com/example/s8161372assignment2/`.
2. Open `LoginViewModelTest` or `DashboardViewModelTest`.
3. Click the green Run icon beside the test class.
4. Verify that all tests pass.

## Project Structure

The project separates responsibilities into:

- `model` – Data models
- `repository` – Repository classes
- `viewmodel` – ViewModels
- `di` – Dependency injection and network configuration
- Fragments – Login, Dashboard and Details user interfaces
- `res` – XML layouts and application resources
- `test` – Unit tests

## Author

**MD PRANTOR MRIDHA**  
Student ID: **8161372**