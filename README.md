# VoiceLab

[![CI](https://github.com/xavimc/voicelab-android/actions/workflows/ci.yml/badge.svg)](https://github.com/xavimc/voicelab-android/actions/workflows/ci.yml)

A playground for Android's platform text-to-speech API (`android.speech.tts.TextToSpeech`),
built with Jetpack Compose.

**Status:** in progress. Done so far: a voice list grouped by language (stateless Compose UI, hoisted selection, own theme) and real engine voices through a ViewModel (StateFlow, SavedStateHandle, mapping, unit tests). Design decisions are recorded as ADRs in the repo. Next: playback controls.

## Build

```bash
./gradlew assembleDebug testDebugUnitTest
```

## License

MIT, see [LICENSE](LICENSE).
