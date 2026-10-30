package io.github.xavimc.voicelab.voices

import kotlinx.coroutines.CompletableDeferred

/** Answers voices() only when the test calls finish(), so the Loading state can be observed. */
class FakeVoiceSource : VoiceSource {
    private val result = CompletableDeferred<List<VoiceOption>?>()
    var closed = false
        private set

    fun finish(voices: List<VoiceOption>?) {
        result.complete(voices)
    }

    override suspend fun voices(): List<VoiceOption>? = result.await()

    override fun close() {
        closed = true
    }
}
