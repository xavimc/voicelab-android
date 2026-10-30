package io.github.xavimc.voicelab.voices

import java.util.Locale

/**
 * A fixed list shaped like what a device with Google's TTS engine reports.
 * The app reads the real engine's voices (TtsVoiceSource); this list feeds previews and tests.
 */
object SampleVoices {
    val all: List<VoiceOption> = listOf(
        VoiceOption("en-us-x-sfg-local", Locale.forLanguageTag("en-US"), VoiceQuality.High, requiresNetwork = false, notInstalled = false),
        VoiceOption("en-us-x-tpf-network", Locale.forLanguageTag("en-US"), VoiceQuality.VeryHigh, requiresNetwork = true, notInstalled = false),
        VoiceOption("en-gb-x-rjs-local", Locale.forLanguageTag("en-GB"), VoiceQuality.High, requiresNetwork = false, notInstalled = true),
        VoiceOption("es-es-x-eea-local", Locale.forLanguageTag("es-ES"), VoiceQuality.High, requiresNetwork = false, notInstalled = false),
        VoiceOption("es-es-x-eed-network", Locale.forLanguageTag("es-ES"), VoiceQuality.VeryHigh, requiresNetwork = true, notInstalled = false),
        VoiceOption("ca-es-x-caf-local", Locale.forLanguageTag("ca-ES"), VoiceQuality.Normal, requiresNetwork = false, notInstalled = false),
        VoiceOption("fr-fr-x-frc-local", Locale.forLanguageTag("fr-FR"), VoiceQuality.High, requiresNetwork = false, notInstalled = true),
    )
}
