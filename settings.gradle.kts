rootProject.name = "liquibase-gradle-test"

pluginManagement {
  repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
  }

  resolutionStrategy {
    eachPlugin {
      if (requested.id.id == "org.liquibase.gradle") {
        useModule("org.liquibase:liquibase-gradle-plugin:3.0.1-SNAPSHOT")
      }
    }
  }
}
