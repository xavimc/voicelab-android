# 6. Upgrade the template's stale dependencies

- **Status:** accepted
- **Date:** 2026-10-25

## Context
The project template pinned `lifecycle` 2.6.1 and `activity-compose` 1.8.0, both well behind.
`collectAsStateWithLifecycle` needs `lifecycle-runtime-compose`, which should match the rest of
`lifecycle`.

## Decision
Upgrade to the latest stable versions, as suggested by Android Studio:
`lifecycle` 2.11.0 (with `lifecycle-runtime-compose` and `lifecycle-viewmodel-compose`),
`activity-compose` 1.13.0, Kotlin 2.4.21 and Compose BOM 2026.09.00. Add
`kotlinx-coroutines-test` 1.11.0 for ViewModel tests. One `lifecycle` version entry drives all
lifecycle artifacts.

## Consequences
- The build and unit tests passed after the upgrade.
- Newer versions can raise the minimum SDK or compile SDK; any such failure is rolled back to the
  last version that builds, and recorded here.
