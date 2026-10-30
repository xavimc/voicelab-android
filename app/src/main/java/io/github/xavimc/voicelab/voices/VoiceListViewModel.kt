package io.github.xavimc.voicelab.voices

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Everything the voice list screen can show. */
sealed interface VoiceListUiState {
    data object Loading : VoiceListUiState
    data class Ready(val voices: List<VoiceOption>, val selectedVoiceName: String?) : VoiceListUiState
    data object EngineUnavailable : VoiceListUiState
}

class VoiceListViewModel(
    private val source: VoiceSource,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow<VoiceListUiState>(VoiceListUiState.Loading)
    val uiState: StateFlow<VoiceListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val voices = source.voices()
            _uiState.value = if (voices == null)
                VoiceListUiState.EngineUnavailable
            else
                VoiceListUiState.Ready(voices, savedStateHandle[SELECTED_KEY])
        }
    }

    fun selectVoice(name: String) {
        savedStateHandle[SELECTED_KEY] = name
        _uiState.update { state ->
            if (state is VoiceListUiState.Ready)
                state.copy(selectedVoiceName = name)
            else
                state
        }
    }

    override fun onCleared() {
        source.close()
    }

    companion object {
        private const val SELECTED_KEY = "selectedVoiceName"

        /** Builds the real ViewModel: real engine, real saved state. */
        val Factory = viewModelFactory {
            initializer {
                VoiceListViewModel(TtsVoiceSource(this[APPLICATION_KEY]!!), createSavedStateHandle())
            }
        }
    }
}
