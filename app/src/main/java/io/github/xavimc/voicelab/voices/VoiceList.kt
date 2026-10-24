package io.github.xavimc.voicelab.voices

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.xavimc.voicelab.ui.theme.VoiceLabTheme
import java.util.Locale

@Composable
fun VoiceListScreen(modifier: Modifier = Modifier) {
    var selectedVoiceName by rememberSaveable { mutableStateOf<String?>(null) }

    VoiceList(
        voices = SampleVoices.all,
        selectedVoiceName = selectedVoiceName,
        onVoiceSelected = { selectedVoiceName = it },
        modifier = modifier,
    )
}

@Composable
fun VoiceList(
    voices: List<VoiceOption>,
    selectedVoiceName: String?,
    onVoiceSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val voicesByLanguage = remember(voices) { voices.groupBy { it.locale } }

    LazyColumn(modifier = modifier) {
        voicesByLanguage.forEach { (locale, voicesInLanguage) ->
            stickyHeader(key = "header-${locale.toLanguageTag()}") {
                LanguageHeader(locale)
            }
            items(voicesInLanguage, key = { it.name }) { voice ->
                VoiceRow(
                    voice = voice,
                    selected = voice.name == selectedVoiceName,
                    onClick = { onVoiceSelected(voice.name) },
                )
            }
        }
    }
}

@Composable
private fun VoiceRow(
    voice: VoiceOption,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItem(
        headlineContent = { Text(voice.name) },
        supportingContent = { Text(voice.details()) },
        leadingContent = { RadioButton(selected = selected, onClick = null) },
        modifier = modifier.selectable(
            selected = selected,
            onClick = onClick,
            role = Role.RadioButton,
        ),
    )
}

@Composable
private fun LanguageHeader(locale: Locale, modifier: Modifier = Modifier) {
    Text(
        text = locale.displayName,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .semantics { heading() },
    )
}

private fun VoiceOption.details(): String = buildList {
    add("Quality: ${quality.name}")
    if (requiresNetwork) add("needs network")
    if (notInstalled) add("not installed")
}.joinToString(" · ")

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun VoiceListDarkPreview() {
    VoiceLabTheme {
        VoiceListScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun VoiceListScreenPreview() {
    VoiceLabTheme {
        VoiceListScreen()
    }
}