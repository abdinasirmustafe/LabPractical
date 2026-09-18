# Refine myCard into a clean Business Card app

Refine the current `MainActivity.kt` into a clean, professional-looking Business Card app. This involves fixing hardcoded string warnings, correcting typos, and improving the layout to follow a "Business Card" design.

## Proposed Changes

### [Component Name] UI and Resources

#### [MODIFY] [strings.xml](file:///C:/Users/moval/Desktop/AIU_Folder/SEMISTER5/Kotlin/Shortsem/Code_project/app/src/main/res/values/strings.xml)
- Add string resources for the business name, owner name, title, and contact information.

#### [MODIFY] [MainActivity.kt](file:///C:/Users/moval/Desktop/AIU_Folder/SEMISTER5/Kotlin/Shortsem/Code_project/app/src/main/java/com/example/mycard/MainActivity.kt)
- Refactor `WelcomeMessage` into a `BusinessCard` composable.
- Implement a more structured layout:
    - Top section: Logo and Business Name.
    - Middle section: Owner Name and Title.
    - Bottom section: Contact information (Email, Phone, Website).
- Use `Scaffold` correctly with `Modifier.padding(innerPadding)`.
- Use string resources instead of hardcoded strings.

## Verification Plan

### Automated Tests
- Run `gradle_build` to ensure the project still compiles.

### Manual Verification
- Render the `WelcomePreview` (which will be renamed to `BusinessCardPreview`) to verify the UI visually.
