plugins {
    id("xyz.jpenilla.run-paper") version "2.1.0"
	id("org.jetbrains.kotlin.jvm") version "1.9.0"
	id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
}

java { toolchain.languageVersion.set(JavaLanguageVersion.of(17)) }

tasks{
    shadowJar {
        minimize() // Will cause issues with Reflection
    }
    runServer {
		minecraftVersion("1.20.1")
	}
}
