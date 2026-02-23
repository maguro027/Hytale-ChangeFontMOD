/**
 * Hytale Change Font MOD - Build Configuration
 * 
 * Language: Java 25
 * Build Tool: Gradle with Kotlin DSL
 * Target: Hytale client-side mod with Hyxin (Mixin loader)
 */

import org.gradle.external.javadoc.StandardJavadocDocletOptions

plugins {
    java
    kotlin("jvm") version "2.1.0"
}

group = "com.maguro027"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

repositories {
    mavenCentral()
    // CurseMaven for Hyxin and other Hytale dependencies
    maven { url = uri("https://cursemaven.com") }
}

dependencies {
    // ===== HYTALE API (Uncomment once SDK is released) =====
    // Hytale Server API - provides Plugin class and core APIs
    // compileOnly("com.hypixel.hytale:server:1.0.0")
    
    // ===== MIXIN FRAMEWORK (Uncomment once SDK is released) =====
    // org.spongepowered:mixin - provides @Mixin, @Inject, @Redirect annotations
    // compileOnly("org.spongepowered:mixin:0.8.5")
    
    // Mixin annotation processor for refmap generation
    // annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
    
    // Runtime inclusion of Mixin for agent-based injection
    // implementation("org.spongepowered:mixin:0.8.5")
    
    // ===== LOGGING =====
    // Standard Java logging (included in JDK)
    
    // ===== TESTING (Optional) =====
    // testImplementation("junit:junit:4.13.2")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(25)
    }
    
    jar {
        from(sourceSets.main.get().output)
        
        manifest {
            attributes(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Implementation-Vendor" to "maguro027",
                "Specification-Title" to "Hytale Change Font MOD",
                "Specification-Version" to project.version,
                "Specification-Vendor" to "maguro027"
            )
        }
    }
    
    clean {
        delete(layout.buildDirectory)
    }
    
    javadoc {
        options.encoding = "UTF-8"
        (options as StandardJavadocDocletOptions).apply {
            addBooleanOption("html5", true)
            addStringOption("Xdoclint", "-html")
        }
    }
}
