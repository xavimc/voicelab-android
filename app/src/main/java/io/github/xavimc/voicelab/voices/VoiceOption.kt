package io.github.xavimc.voicelab.voices

import java.util.Locale

/**
 * A TTS voice as the UI sees it.
 *
 * Mirrors the fields of the platform's android.speech.tts.Voice that the app uses, but is our
 * own type: the UI, previews and tests never need a device or a TextToSpeech instance.
 * See docs/adr/0001-own-voice-model.md.
 */
data class VoiceOption(
    /** Unique per engine, e.g. "en-us-x-sfg-local". Used as the list key and selection id. */
    val name: String,
    val locale: Locale,
    val quality: VoiceQuality,
    /** Voice.isNetworkConnectionRequired: synthesis needs a network connection. */
    val requiresNetwork: Boolean,
    /** Voice.getFeatures() contains KEY_FEATURE_NOT_INSTALLED: data must be downloaded first. */
    val notInstalled: Boolean,
)

/** Voice.QUALITY_VERY_LOW (100) .. Voice.QUALITY_VERY_HIGH (500). */
enum class VoiceQuality { VeryLow, Low, Normal, High, VeryHigh }
