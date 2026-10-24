# 2. Hoist the selected voice out of the list

- **Status:** accepted
- **Date:** 2026-10-13

## Context
The list shows which voice is selected and lets the user change it. Something has to own that
state, and the owner will change: first the screen, later a ViewModel.

## Decision
`VoiceList` is stateless: it receives the voices and the selected voice's name, and reports clicks
through `onVoiceSelected`. A stateful `VoiceListScreen` above it owns the selection with
`rememberSaveable`, so it survives configuration changes such as rotation.

## Consequences
- `VoiceList` can be previewed and tested in any state just by passing parameters.
- Moving the state into a ViewModel later replaces the wrapper only; `VoiceList` does not change.
- `rememberSaveable` survives rotation and process death but not leaving the screen; that is
  accepted until the selection is persisted.
