# 3. Voice list grouped by language, rows as radio buttons

- **Status:** accepted
- **Date:** 2026-10-13

## Context
A device can report dozens of voices across many languages. The user picks exactly one.

## Decision
A `LazyColumn` grouped by language with sticky headers. Each row is a Material 3 `ListItem` with a
`RadioButton`; the whole row is the touch target via `Modifier.selectable(role = Role.RadioButton)`,
and items are keyed by voice name.

## Consequences
- Only visible rows are composed, so long lists stay cheap.
- Accessibility services announce rows as radio buttons with their selected state, and UI tests
  can assert on the same semantics.
- Stable keys keep row state and animations correct when the list changes.
