# 5. A ViewModel owns the voice list, fed through a VoiceSource

- **Status:** accepted
- **Date:** 2026-10-25

## Context
The voice list now comes from the device's TTS engine, which starts asynchronously
(`OnInitListener`) and must be shut down. That state has to outlive a composable and survive
rotation. ADR 0002 planned for the selection to move from the screen into a ViewModel.

## Decision
- `VoiceListViewModel` owns the screen state and exposes it as a `StateFlow`, which the screen
  collects with `collectAsStateWithLifecycle`.
- The state is one sealed type: loading, ready (voices and selection), or engine unavailable.
- The ViewModel depends on a `VoiceSource` interface, not on `TextToSpeech`. The real
  `TtsVoiceSource` turns `OnInitListener` into a suspend call with `suspendCancellableCoroutine`
  (init happens once, so it is not a Flow). The ViewModel closes the source in `onCleared()`.
- The selected voice is kept in `SavedStateHandle`, so it still survives process death as it did
  with `rememberSaveable`.
- The `Voice` to `VoiceOption` mapping is a function on plain values, tested on the JVM. Quality
  values between the platform's constants round down; anything below `QUALITY_VERY_LOW` is `VeryLow`.
- No dependency injection framework: a `viewModelFactory` is enough for one screen.

## Consequences
- The ViewModel and the mapping are unit-tested on the JVM with a fake source; no device needed.
- The one-line `Voice.toVoiceOption()` adapter and `TtsVoiceSource` are only covered on a device,
  by the instrumented tests planned later.
- `VoiceList` does not change; only `VoiceListScreen` does (ADR 0002).
- The engine-unavailable state shows a plain message until the error states are designed.
