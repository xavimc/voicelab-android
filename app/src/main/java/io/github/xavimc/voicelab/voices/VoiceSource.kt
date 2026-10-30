package io.github.xavimc.voicelab.voices

/**
 * Where the voice list comes from. The ViewModel depends on this, not on TextToSpeech,
 * so its tests run on the JVM with a fake. See docs/adr/0005-voice-list-viewmodel.md.
 */
interface VoiceSource {
    /** The engine's voices, or null if no engine could be started. */
    suspend fun voices(): List<VoiceOption>?

    /** Releases the engine. Safe to call more than once. */
    fun close()
}
