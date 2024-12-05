# MyPersonalDiary7

MyPersonalDiary7 is a convenient, simple, intuitive and feature-rich Android diary app. It allows users to create, view, edit and delete diary entries with multimedia support (you can add an image and change it). The app uses modern Android development techniques, including MVVM architecture, Jetpack libraries and the Room database.

## Features

- Create, view, edit and delete entries: You can add diary entries with text and add images to an entry. You can preview an entry by clicking on it, edit or delete existing entries, and change the image.
- Multimedia support: Add images to entries using the device file system. Images are securely stored using URIs.
- View calendar: You can view the calendar.
- List View: View and manage all the entries in a scrollable list, the list can be scrolled by tapping on it and swiping.
- Seamless Data Management: All entries are securely stored in the local Room database, ensuring that data is preserved between app sessions.

- Reactive Data Handling: The app uses LiveData to automatically update the UI when data changes.

## Architecture

The app is built using the MVVM (Model-View-ViewModel) architecture:

- Model: The Room database with DiaryEntries and DiaryEntriesDao to handle CRUD operations.
- ViewModel: DiaryEntriesViewModel handles the business logic and serves as a bridge between the UI and the database.
- View: Activities such as MainActivity, AddEntryActivity, EditEntryActivity, CalendarActivity interact with the user and present data.

## Core Components

### Room Database
- DiaryEntries: Represents the database table structure.
- DiaryEntriesDao: Provides methods for performing CRUD operations.
- DiaryEntriesDatabase: Sets up the Room database and supports migrations.

### Jetpack Components
- LiveData: Monitors database changes in real time and automatically updates the UI.
- ViewModel: Manages application data and provides separation of concerns.

### Activities
- MainActivity: Displays a list of diary entries using RecyclerView.
- AddEntryActivity: Allows users to add new entries with text and images.
- EditEntryActivity: Allows users to view, edit, or delete existing entries and change the image.
- CalendarActivity: Displays the calendar.

### RecyclerView and Adapter
- DiaryEntriesAdapter: Manages the display of diary entries in the list. It binds data to individual list items and handles user interactions.

### Handling Media
- Images are attached to entries using Intent.ACTION_OPEN_DOCUMENT.
- Persistent access to images is ensured using contentResolver.takePersistableUriPermission.

## How it works

1. Adding entries:
- Navigate to AddEntryActivity from the main screen (press the plus button at the bottom of the screen).
- Enter text, optionally select an image, and save the entry.
- The entry is saved to the Room database, and the RecyclerView in MainActivity is automatically updated via LiveData.

2. Edit, view, and delete entries:
- Click on an entry in the list to open EditEntryActivity.
- Edit the text or image and save the changes. Or delete the entry.
- Updates are reflected instantly in the database and UI.

3. View the calendar:
- Open CalendarActivity by clicking the button with the calendar image at the bottom.

4. Save data:
- All data is stored in the Room database, ensuring persistence between app sessions.
- Images are stored as URIs with permissions granted for long-term access.

## Installation

1. Clone the repository.

2. Open the project in Android Studio.

3. Sync Gradle and install dependencies.

4. Run the app on an emulator or physical device.
 
 ## Minimum requirements
- Android 6.0 (API 23) or higher
- Android Studio 4.0 or higher installed
