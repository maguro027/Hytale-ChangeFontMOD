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
    // ===== HYTALE SERVER API =====
    // Provides Plugin base class and core game interfaces
    // NOTE: The Server API is bundled with Hyxin loader at runtime
    // For development, it's available in the official Hytale SDK from Hypixel
    // compileOnly("com.hypixel.hytale:Server:1.0.+")
    
    // ===== MIXIN FRAMEWORK 0.8.5 =====
    // Annotation-based bytecode manipulation for method/field hooking
    compileOnly("org.spongepowered:mixin:0.8.5")
    
    // Mixin annotation processor - generates refmap.json for method mapping
    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
    
    // Runtime Mixin library - included in fat JAR for agent-based injection
    implementation("org.spongepowered:mixin:0.8.5")
    
    // ===== LOGGING (built-in with Java) =====
    
    // ===== TESTING =====
    testImplementation("junit:junit:4.13.2")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(25)
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
