package io.github.xavimc.voicelab.voices

import androidx.lifecycle.SavedStateHandle
import io.github.xavimc.voicelab.MainDispatcherRule
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class VoiceListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val source = FakeVoiceSource()

    private fun createViewModel(saved: SavedStateHandle = SavedStateHandle()) =
        VoiceListViewModel(source, saved)

    @Test
    fun startsLoading() = runTest {
        val viewModel = createViewModel()

        assertEquals(VoiceListUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun voicesArriveAsReady() = runTest {
        val viewModel = createViewModel()

        source.finish(SampleVoices.all)
        advanceUntilIdle()

        assertEquals(VoiceListUiState.Ready(SampleVoices.all, null), viewModel.uiState.value)
    }

    @Test
    fun noEngineIsUnavailable() = runTest {
        val viewModel = createViewModel()

        source.finish(null)
        advanceUntilIdle()

        assertEquals(VoiceListUiState.EngineUnavailable, viewModel.uiState.value)
    }

    @Test
    fun selectingAVoiceUpdatesReady() = runTest {
        val viewModel = createViewModel()

        source.finish(SampleVoices.all)
        advanceUntilIdle()
        viewModel.selectVoice("es-es-x-eea-local")
        assertEquals(VoiceListUiState.Ready(SampleVoices.all, "es-es-x-eea-local"), viewModel.uiState.value)
    }

    @Test
    fun savedSelectionIsRestored() = runTest {
        val viewModel = createViewModel(SavedStateHandle(mapOf("selectedVoiceName" to "es-es-x-eea-local")))

        source.finish(SampleVoices.all)
        advanceUntilIdle()

        assertEquals(VoiceListUiState.Ready(SampleVoices.all, "es-es-x-eea-local"), viewModel.uiState.value)
    }
}
