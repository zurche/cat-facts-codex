# Cat Facts Codex 🐱

Cat Facts Codex is a minimalist Android application that showcases a modern, testable Compose stack. It fetches random cat facts from the public [`catfact.ninja`](https://catfact.ninja/) API and lets you request fresh facts on demand.

## Features
- Material 3 UI built entirely with Jetpack Compose.
- MVVM presentation layer backed by Kotlin coroutines and StateFlow.
- Manual dependency container that wires Retrofit, OkHttp, and kotlinx.serialization.
- Clear loading, success, and error states with retry support.
- Unit-tested ViewModel logic using TestDispatcher and fake repositories.

## Tech Stack
- Kotlin, Jetpack Compose, Material 3
- AndroidX Lifecycle (ViewModel, Compose integration)
- Kotlin coroutines & StateFlow
- Retrofit 2 + kotlinx.serialization converter
- OkHttp with logging interceptor

## Architecture
```
app/
├── data/              # Retrofit service + repository implementation
├── di/                # Application-level dependency container
├── domain/            # Domain models and repository abstraction
├── ui/                # Compose screens, ViewModel, UI state
└── CatFactsApplication.kt
```

- **Presentation** — `CatFactViewModel` exposes a `StateFlow<CatFactUiState>` consumed by Compose.
- **Domain** — `CatFactRepository` defines the contract for fetching facts.
- **Data** — `CatFactRepositoryImpl` bridges the API service (`CatFactApiService`) with the domain layer.
- **DI** — `DefaultAppContainer` wires dependencies and is exposed via `CatFactsApplication`.

## Getting Started

### Prerequisites
- Android Studio Ladybug or newer (with JDK 17 configured)  
  _If running Gradle from the CLI, ensure `JAVA_HOME` points to a compatible JDK._
- An Android device or emulator (API level 24+)

### Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/<your-username>/cat-facts-codex.git
   cd cat-facts-codex
   ```
2. Open the project in Android Studio and let Gradle sync.

### Run the app
- Choose a device (physical or emulator) and press **Run** from Android Studio, or invoke:
  ```bash
  ./gradlew installDebug
  ```

### Run tests
```bash
./gradlew test
```
> Note: The command requires a local JDK. If you see _“Unable to locate a Java Runtime”_, install a JDK (Temurin/OpenJDK 17+), update `JAVA_HOME`, and rerun.

## API
- Endpoint: `GET https://catfact.ninja/fact`
- Response sample:
  ```json
  {
    "fact": "Cats can jump up to six times their length.",
    "length": 52
  }
  ```

## Contributing
Issues and pull requests are welcome. Feel free to suggest improvements, new features, or additional tests.

---

Enjoy the cat facts! 😺
