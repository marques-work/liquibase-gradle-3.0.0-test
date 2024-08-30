import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.liquibase)
}

group = "com.liquibasetest"
version = "0.0.1-SNAPSHOT"

val sourceLangLevel = JavaVersion.VERSION_21
val jvmCompileTarget = JavaVersion.VERSION_21

java {
  sourceCompatibility = sourceLangLevel
  targetCompatibility = jvmCompileTarget
}

repositories {
  mavenCentral()
}

configurations.getByName("liquibaseRuntime").extendsFrom(configurations.getByName("implementation"))

dependencies {
  implementation(libs.kotlin.stdlib)
  implementation(libs.liquibase.core)
  implementation(libs.jdbc.postgresql)

  liquibaseRuntime(libs.picocli) // required *only* by the liquibase command line
}

tasks.withType<KotlinCompile> {
  compilerOptions {
    apiVersion.set(KOTLIN_2_0)
    freeCompilerArgs.add("-Xjsr305=strict")
    jvmTarget = JvmTarget.JVM_21
  }
}

// current working directory
val thisDir: String = System.getProperty("user.dir")

liquibase { // configure liquibase gradle task with local dev db connection details
  activities {
    create("main") {
      arguments = mapOf(
        // the liquibase gradle task is nothing more than a frontend to the liquibase command-line runner.
        // this key-value map will be transformed into command-line flags and passed to the runner.
        "changelog-file" to "changelog.xml",
        "search-path" to "$thisDir/src/main/resources",
        "url" to "jdbc:postgresql://localhost:6432/postgres",
        "username" to "postgres",
        "password" to "postgres",
        "driver" to "org.postgresql.Driver",
         "log-level" to "DEBUG",
      )
    }
  }
}
