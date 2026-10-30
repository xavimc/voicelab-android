package io.github.xavimc.voicelab.voices

import android.content.Context
import android.speech.tts.TextToSpeech
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/** Reads voices from the device's default TTS engine. */
class TtsVoiceSource(context: Context) : VoiceSource {
    private val appContext = context.applicationContext
    private var engine: TextToSpeech? = null

    override suspend fun voices(): List<VoiceOption>? {
        val tts = engine ?: start() ?: return null
        return tts.voices.orEmpty().map { it.toVoiceOption() }
    }

    override fun close() {
        engine?.shutdown()
        engine = null
    }

    private suspend fun start(): TextToSpeech? {
        var created: TextToSpeech? = null
        // Some engines call onInit before the constructor returns, so the callback must not
        // touch `created`. It only hands over the status; the voices are read after resuming.
        val status = try {
            suspendCancellableCoroutine<Int> { continuation ->
                created = TextToSpeech(appContext) { status ->
                    if (continuation.isActive) continuation.resume(status)
                }
            }
        } catch (e: CancellationException) {
            // Cancelled before or just after onInit: either way the engine is ours to release.
            created?.shutdown()
            throw e
        }
        val tts = checkNotNull(created)
        if (status != TextToSpeech.SUCCESS) {
            tts.shutdown()
            return null
        }
        engine = tts
        return tts
    }
}
