import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.JavaVersion
import kotlin.concurrent.thread
import java.io.*

plugins {
    val kotlinVersion = "1.2.60"

    application
    java
    kotlin("jvm") version kotlinVersion
}

group = "io.mikael.bug"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("com.github.ajalt:clikt:1.3.0")
}

application {
    mainClassName = "bug.ReproKt"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.javaParameters = true
}

task("graalNativeImage") {
    doLast {
        val jars = configurations.default.map { it.absolutePath }.joinToString(separator = ":")
        val params = arrayOf(
                "/home/mikael/local/graalvm/bin/native-image",
                application.mainClassName,
                "--verbose",
                "-H:ReflectionConfigurationFiles=reflection.json",
                "-H:IncludeResources=.*",
                "-H:+ReportUnsupportedElementsAtRuntime",
                "-J-Dgraal.InlineDuringParsingMaxDepth=10",
                "-cp", "build/classes/kotlin/main/:build/resources/main/:$jars"
        )

        println(params.joinToString(separator = " "))
        val pb = ProcessBuilder(*params)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectErrorStream(true)
                .start()

        val outputReaderThread = thread {
            val r = BufferedReader(InputStreamReader(pb.inputStream, "UTF-8"))
            r.lines().forEach { l -> println(l) }
        }

        pb.waitFor()
        outputReaderThread.join()
    }
}
