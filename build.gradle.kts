/**
 * Hytale Change Font MOD - Build Configuration
 * 
 * Language: Java 25
 * Build Tool: Gradle with Kotlin DSL (8.x compatible)
 * Target: Hytale Early Plugin (Hyxin 0.0.11+) with Mixin injection
 * 
 * Features:
 * - ShadowJar for fat JAR (all deps included)
 * - Mixin 0.8.5 with annotation processing
 * - Hytale Server API integration
 * - Java 25 LTS toolchain
 */

import org.gradle.external.javadoc.StandardJavadocDocletOptions
import org.gradle.api.file.DuplicatesStrategy

plugins {
    java
    kotlin("jvm") version "2.1.0"
    // Note: ShadowJar plugin is optional for this development build
    // For production builds, uncomment and use: id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.maguro027"
version = "1.0.0"

description = "Hytale Change Font MOD - Replace chat font with custom TTF via Mixin injection"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

repositories {
    mavenCentral()
    
    // SpongePowered repository for Mixin framework
    maven {
        url = uri("https://repo.spongepowered.org/maven")
        name = "SpongePowered"
    }
    
    // CurseMaven repository for Hytale dependencies and Hyxin
    maven { 
        url = uri("https://cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }
}

dependencies {
    // ===== HYXIN EARLY PLUGIN FRAMEWORK =====
    // Hyxin is NOT a compile-time dependency - it's provided at runtime by the loader
    // The Plugin base class and APIs are available ONLY during execution
    // DO NOT add Hyxin as a dependency here - it causes version conflicts
    
    // ===== HYTALE SERVER API =====
    // Provides Plugin base class and core game interfaces
    // Server API is bundled with Hyxin loader at runtime
    // NOT available in public repositories - disabled for development builds
    // Uncomment only if you have special access to Hypixel's SDK
    // compileOnly("com.hypixel.hytale:Server:1.0.+")
    
    // ===== MIXIN FRAMEWORK 0.8.5 =====
    // CRITICAL FOR EARLY PLUGIN SAFETY:
    // - compileOnly: Used only during compilation to resolve @Mixin annotations
    //   This prevents accidental inclusion of Mixin that might conflict with Hyxin's version
    // - annotationProcessor: Generates refmap.json for method mapping (local use only)
    // - implementation: Bundled in JAR as fallback (required for injection at runtime)
    
    // Annotation-based bytecode manipulation for method/field hooking
    compileOnly("org.spongepowered:mixin:0.8.5")
    
    // Mixin annotation processor - generates refmap.json for method mapping
    // Must be on annotationProcessor configuration for Gradle to use it during compile
    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
    
    // Runtime Mixin library - included in fat JAR for agent-based injection
    // This is the ACTUAL runtime dependency that will be bundled
    implementation("org.spongepowered:mixin:0.8.5")
    
    // ===== LOGGING =====
    // Java's built-in java.util.logging or SLF4J
    // No external logging dependencies needed for this simple plugin
    
    // ===== TESTING =====
    testImplementation("junit:junit:4.13.2")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(25)
        
        // ===== MIXIN ANNOTATION PROCESSING =====
        // The annotationProcessor configuration will automatically inject the Mixin processor
        // We just need to pass the refmap arguments
        options.compilerArgs.addAll(listOf(
            "-Amixin.refmap=hytalemod.refmap.json",
            "-Amixin.default.obf=srg"
        ))
    }
    
    jar {
        from(sourceSets.main.get().output)
        
        // Allow duplicate resources (Mixin config may be in multiple locations)
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        
        manifest {
            attributes(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Implementation-Vendor" to "maguro027",
                "Specification-Title" to "Hytale Change Font MOD",
                "Specification-Version" to project.version,
                "Specification-Vendor" to "maguro027",
                "Multi-Release" to "true",
                "Created-By" to "Gradle ${gradle.gradleVersion}"
            )
        }
        
        // Include resource files (fonts, configs, mixins)
        from(sourceSets.main.get().resources)
    }
    
    // Build a fat JAR with all dependencies included
    // Note: shadowJar task is preferred for production, but using custom configuration for compatibility
    register<Jar>("fat JAR") {
        group = "build"
        description = "Build a fat JAR with all dependencies"
        
        archiveBaseName.set("hytale-changefont-mod")
        archiveVersion.set("1.0")
        archiveClassifier.set("")
        
        manifest {
            attributes(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Implementation-Vendor" to "maguro027",
                "Multi-Release" to "true"
            )
        }
        
        from(sourceSets.main.get().output)
        from(sourceSets.main.get().resources)
        
        configurations.runtimeClasspath.get().forEach { file ->
            if (file.isDirectory) {
                from(file)
            } else {
                from(zipTree(file))
            }
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
