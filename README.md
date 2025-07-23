# 🌤️ Weather App

A simple and modern Android Weather App built using Kotlin and Jetpack Compose.  
The app fetches current weather data based on the user's **device location** or **city name** using the WeatherAPI service.

---

## 📱 Features

- 📍 Detects user's current location.
- ⚠️ Prompts user to enable location if it's turned off.
- 🔐 Requests location permission at runtime.
- 🌐 Fetches weather data via **Retrofit**.
- 🏙️ Supports weather search by city name.
- 🧭 Navigates between location-based and search-based weather screens.

---

## 🚀 Getting Started

Follow the steps below to run this project on your local machine.

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/weather-app.git
cd weather-app
```
### 2. Get a Free API Key from WeatherAPI
- Visit https://www.weatherapi.com.
- Create a free account and log in.
- Copy your unique API key from the dashboard.

### 3. Add Your API Key to the Project
- Open the project in Android Studio.

Go to the following file:
```bash
app/src/main/java/com/yourpackage/ApiSetup/Api_keys.kt
```
- Replace the placeholder with your API key:
```bash
const val API_KEY = "your_api_key_here"
```
<img width="405" height="870" alt="image" src="https://github.com/user-attachments/assets/c27f6db0-a7a8-49df-bc3e-ad7c63bac025" />
<img width="409" height="873" alt="image" src="https://github.com/user-attachments/assets/b43ae79b-ac42-48fe-ad8a-e69a04d7420d" />

###Contact
Have question?/suggestion?
reach out @farazsiddiqui2003@gmail.com
