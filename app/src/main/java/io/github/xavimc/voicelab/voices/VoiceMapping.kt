package io.github.xavimc.voicelab.voices

import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import java.util.Locale

/** Thin on purpose: the platform Voice can't be built in JVM tests, so the logic is in voiceOptionOf. */
fun Voice.toVoiceOption(): VoiceOption =
    voiceOptionOf(name, locale, quality, isNetworkConnectionRequired, features.orEmpty())

/**
 * Maps a voice's raw fields to our model. Quality values between the platform's constants
 * round down to the nearest one; anything below QUALITY_VERY_LOW is VeryLow.
 */
fun voiceOptionOf(
    name: String,
    locale: Locale,
    quality: Int,
    requiresNetwork: Boolean,
    features: Set<String>,
): VoiceOption {
    val voiceQuality = when {
        quality >= Voice.QUALITY_VERY_HIGH -> VoiceQuality.VeryHigh
        quality >= Voice.QUALITY_HIGH -> VoiceQuality.High
        quality >= Voice.QUALITY_NORMAL -> VoiceQuality.Normal
        quality >= Voice.QUALITY_LOW -> VoiceQuality.Low
        else -> VoiceQuality.VeryLow
    }
    return VoiceOption(
        name = name,
        locale = locale,
        quality = voiceQuality,
        requiresNetwork = requiresNetwork,
        notInstalled = TextToSpeech.Engine.KEY_FEATURE_NOT_INSTALLED in features,
    )
}
