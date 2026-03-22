# 📱 Yuva Help Android App

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-brightgreen?logo=android" />
  <img src="https://img.shields.io/badge/Language-Kotlin-blueviolet?logo=kotlin" />
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-blue" />
  <img src="https://img.shields.io/badge/Min%20SDK-24-orange" />
  <img src="https://img.shields.io/github/license/yuvahelp/yuva-help-android" />
</p>

> Official Android app for [yuva.help](https://yuva.help) — delivering Jobs, Results, Admit Cards, Government Schemes, and Education News to youth across India.

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🔄 Auto Sync | Fetches latest WordPress posts from `yuva.help` |
| 🏠 Home Feed | Modern card UI with thumbnail, title, excerpt |
| 📂 Categories | Latest Jobs · Results · Admit Cards · Govt Schemes · Education |
| 📰 Article Reader | Featured image, full content, share & open in browser |
| 🔍 Search | Full-text search across all cached posts |
| 📴 Offline Mode | Room database for reading without internet |
| 🔔 Notifications | Local notification on new content (Android 13+ aware) |
| ⏱️ Background Sync | Automatic hourly refresh via WorkManager |

---

## 🛠️ Tech Stack

| Layer | Library |
|-------|---------|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Networking | Retrofit 2 + Moshi |
| Database | Room |
| Background | WorkManager |
| Images | Coil |
| Architecture | MVVM + Repository |

---

## 🚀 Getting Started

### Requirements
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 34

### Clone & Open

```bash
git clone https://github.com/yuvahelp/yuva-help-android.git
```

Open the folder in Android Studio → it will sync Gradle automatically → press ▶ Run.

### Build from CLI

```bash
chmod +x gradlew
./gradlew assembleDebug
# APK → app/build/outputs/apk/debug/app-debug.apk
```

### Run Tests

```bash
./gradlew test
```

---

## 📁 Project Structure

```
app/src/main/java/com/yuvahelp/app/
├── data/
│   ├── local/          # Room DB — AppDatabase, PostDao, PostEntity
│   ├── model/          # Post, WordPressPostDto
│   └── remote/         # Retrofit — WordPressApi, ApiModule
├── repository/         # PostRepository (single source of truth)
├── ui/
│   ├── navigation/     # YuvaHelpApp + NavGraph
│   ├── screens/        # HomeScreen, CategoriesScreen, ArticleScreen, SearchScreen …
│   └── viewmodel/      # PostViewModel + PostViewModelFactory
└── worker/             # SyncWorker (WorkManager hourly sync)
```

---

## 🗺️ Roadmap

- [ ] FCM push notifications from WordPress
- [ ] Hilt dependency injection
- [ ] Dark mode
- [ ] Infinite scroll / pagination
- [ ] Bookmarks / Favourites
- [ ] Play Store release

---

## 🤝 Contributing

See [CONTRIBUTING.md](.github/CONTRIBUTING.md). PRs welcome!

---

## 📄 License

MIT License — see [LICENSE](LICENSE) for details.

---

<p align="center">Made with ❤️ for <a href="https://yuva.help">yuva.help</a></p>
