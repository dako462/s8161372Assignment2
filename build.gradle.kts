// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false

    // Code generation for Hilt
    id("com.google.devtools.ksp") version "2.3.6" apply false

    // Hilt Dependency Injection
    id("com.google.dagger.hilt.android") version "2.60.1" apply false
}