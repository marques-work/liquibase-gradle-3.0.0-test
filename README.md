# Testing Liquibase Gradle Plugin Upgrade to 3.0.0

This project is a simple example to help debug issues moving from Liquibase Gradle Plugin `2.2.2` to `3.0.0` in a Kotlin
project.

## Versions

Just to get the preliminary questions out of the way, here are the liquibase and picocli versions I'm using:

| Library   | Version |
|-----------|---------|
| Liquibase | 4.29.1  |
| picocli   | 4.7.6   |

You can find this in the [gradle/libs.versions.toml](./gradle/libs.versions.toml) file.

## Usage

Everything can be orchestrated through `make`. This project uses `docker compose` to set up a test database.

```bash
# prints all available tasks; alias for `make help`
make

# uses `docker compose` to setup a PostgreSQL database on
# non-standard port 6432 to avoid colliding with other local
# PostgreSQL instances
make setup

# runs the Liquibase migrations using gradle
make migrate

# optionally open a `psql` session to the database
make psql

# teardown the PostgreSQL database container
make teardown
```

## Issue

On the `main` branch, the project uses `liquibase-gradle-plugin` version `2.2.2`. The migrations run successfully.

On the `upgrade` branch, the project uses `liquibase-gradle-plugin` version `3.0.0`. The migrations fail to run. It
appears that the upgraded plugin is not sending command-line arguments to the Liquibase CLI correctly (or at all).

You can use the above `make` commands to test in each branch separately.
