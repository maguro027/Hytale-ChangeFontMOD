plugins {
    java
    kotlin("jvm") version "1.9.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"  // JARシェイド
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
    // Hyxin依存（CurseMavenで）: https://cursemaven.com/
    maven { url = uri("https://cursemaven.com") }
}

dependencies {
    // Hytale Server API (公式Mavenから) - リリース後に有効化
    // compileOnly("com.hypixel.hytale:Server:1.0.+")
    // Mixin (Hyxin経由) - リリース後に有効化
    // compileOnly("org.spongepowered:mixin:0.8.5")
    // annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
    // implementation("org.spongepowered:mixin:0.8.5")
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        destinationDirectory.set(file("build/libs"))
    }
    build {
        dependsOn(shadowJar)
    }
    // runタスク (テストサーバー)
    named<JavaExec>("run") {
        classpath = sourceSets.main.get().runtimeClasspath
        jvmArgs = listOf("-Xmx4G")
        // HytaleServer JARパスを環境変数で指定
    }
}
