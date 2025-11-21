🌐 Tech Conference App – Android (Jetpack Compose)

A modern, clean UI Android application built using Kotlin and Jetpack Compose for showcasing a fictional Tech Conference 2025.
The app displays speakers, sessions, keynotes, filters, reviews, and interactive components like “Watch Live” and “Calendar”.

✨ Features
🎙️ Featured Speakers

Horizontal scrollable list of speakers

Name initials as avatars

Clean card UI for profile preview

🗓️ Conference Schedule

Filter sessions by:

All

Keynote

Workshop

Networking

Beautiful custom session cards

Special keynote card styling

⭐ Reviews Section

User reviews of keynote sessions

Star rating UI

Displayed under all categories

🎥 Bottom Actions

Watch Live button

Calendar button

Toast interactions for demo behavior

🎨 Modern UI

Fully based on Jetpack Compose

Rounded cards, gradients, dark theme

Responsive layout

📸 Screenshots

(Add your images here once uploaded to GitHub)

![Banner](images/banner.png)
![Speakers](images/speakers.png)
![Schedule](images/schedule.png)
![Reviews](images/reviews.png)

🛠️ Tech Stack

Kotlin

Jetpack Compose

Material 3

LazyColumn & LazyRow

State Management (remember, mutableStateOf)

Android Studio Hedgehog+

📁 Project Structure
/MainActivity.kt
|– TechConferenceApp()
|– ConferenceHomeScreen()
|– SpeakerCard()
|– SessionCard()
|– KeynoteSessionCard()
|– ReviewCard()
|– Data models (Speaker, Session, Review)
|– Fake data lists (speakers, sessions, keynoteReviews)

🚀 How to Run

Clone this repository:

git clone https://github.com/your-username/your-repo-name.git


Open the project in Android Studio

Sync Gradle

Run the app on an emulator or physical device

🤝 Contributing

Feel free to fork the repo and submit pull requests.
UI improvements, animations, and new screens are welcome!

📜 License

This project is open-source under the MIT License.
You are free to use, modify, and distribute.
