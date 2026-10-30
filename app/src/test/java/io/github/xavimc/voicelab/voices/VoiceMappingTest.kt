package io.github.xavimc.voicelab.voices

import android.speech.tts.Voice
import java.util.Locale
import org.junit.Assert.assertEquals
import org.junit.Test

class VoiceMappingTest {

    private fun map(quality: Int = Voice.QUALITY_NORMAL, features: Set<String> = emptySet()) =
        voiceOptionOf("en-us-x-test", Locale.US, quality, requiresNetwork = false, features = features)

    @Test
    fun exactQualityMapsToItsLevel() {
        assertEquals(VoiceQuality.High, map(quality = Voice.QUALITY_HIGH).quality)
    }

    @Test
    fun qualityBetweenLevelsRoundsDown() {
        assertEquals(VoiceQuality.Normal, map(quality = 350).quality)
    }

    @Test
    fun qualityBelowLowestIsVeryLow() {
        assertEquals(VoiceQuality.VeryLow, map(quality = 50).quality)
    }

    @Test
    fun notInstalledFeatureSetsFlag() {
        assertEquals(true, map(features = setOf("notInstalled")).notInstalled)
    }

}