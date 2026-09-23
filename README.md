# AI Business Dashboard — Multiplatform

One Kotlin codebase that runs as:
- an Android app
- a Windows desktop app
- a Mac desktop app
- a Linux desktop app

## How building works (no installs needed on your PC)

This repo has a GitHub Actions workflow (`.github/workflows/build.yml`) that
automatically builds all four versions in the cloud every time you push to
the `main` branch — or you can trigger it manually from the **Actions** tab
on GitHub ("Run workflow" button).

When it finishes (takes a few minutes), open the workflow run on GitHub and
scroll down to **Artifacts**. You'll see:
- `android-apk` → install on your phone
- `windows-installer` → `.msi` installer for Windows
- `mac-installer` → `.dmg` for Mac
- `linux-installer` → `.deb` for Linux

You never need Android Studio, Gradle, or Xcode on your own machine.

## Project structure

```
composeApp/
  src/
    commonMain/   -> shared UI + logic (used by ALL platforms)
    androidMain/  -> Android-only entry point
    desktopMain/  -> Windows/Mac/Linux entry point
```

Almost everything lives in `commonMain` — that's the whole point of
Compose Multiplatform: write once, run everywhere.

## What's implemented so far

- Login (demo), Dashboard, Generate, Result, History, Settings screens
- Format / Style / Tone selection for content generation
- Placeholder AI generation (returns formatted mock text instantly)
- In-memory history (resets when the app restarts — a real database can be
  added later without changing the UI)

## Next steps (not done yet)

- Wire up a real AI API call in `AppState.generate()` using the API key from
  Settings (e.g. via Ktor's multiplatform HTTP client)
- Add persistent storage (SQLite via SQLDelight, or a JSON file) so history
  survives restarts
- App icons for Android/desktop
