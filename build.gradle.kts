plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.7.22"
    id("org.jetbrains.intellij") version "1.11.0"
}

group = "id.andgen"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":lib"))
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2021.3.1.16")
    type.set("AI") // Target IDE Platform

    plugins.set(
        listOf(
            "android",
        )
    )
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    patchPluginXml {
        sinceBuild.set("212")
        untilBuild.set("213.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
