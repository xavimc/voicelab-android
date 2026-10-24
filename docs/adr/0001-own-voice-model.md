# 1. Own voice model, fed by a fixed list until the engine is wired

- **Status:** accepted
- **Date:** 2026-10-13

## Context
The voice list needs data. The real source, `TextToSpeech.getVoices()`, is only valid after the
engine finishes initialising asynchronously (`OnInitListener`). Handling that properly needs state
that outlives a composable, which is the ViewModel work planned next.

## Decision
The UI works on our own `VoiceOption` data class, whose fields mirror the parts of
`android.speech.tts.Voice` the app uses (name, locale, quality, network required, not installed).
For now it is fed by a fixed in-memory list, `SampleVoices`.

## Consequences
- The screen, its previews and its tests run without a device or a TTS engine.
- Wiring the real engine changes the source of the list, not the screen.
- A mapping from `Voice` to `VoiceOption` has to be written and tested when the engine is wired.
- Risk: the model drifts from what engines really report. Mitigated by mirroring `Voice`'s fields.
