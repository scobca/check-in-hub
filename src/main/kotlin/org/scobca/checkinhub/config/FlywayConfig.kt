package org.scobca.checkinhub.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

/**
 * Spring configuration class for Flyway database migrations.
 *
 * This configuration sets up a dedicated [DataSource] backed by HikariCP
 * and initializes Flyway to apply database schema migrations on startup
 * using the provided [DatabaseProperties].
 *
 * @property databaseProperties The database connection properties used to build the migration data source.
 */
@Configuration
class FlywayConfig(private val databaseProperties: DatabaseProperties) {

    /**
     * Creates a [DataSource] backed by [HikariDataSource] for Flyway migrations.
     *
     * The JDBC URL, username, password, and schema are derived from [databaseProperties]
     * and configured on a new [HikariConfig] instance.
     *
     * @return A configured [DataSource] instance for PostgreSQL connections used by Flyway.
     */
    @Bean
    fun dataSource(): DataSource {
        val config = HikariConfig().apply {
            jdbcUrl =
                "jdbc:postgresql://${databaseProperties.host}:${databaseProperties.port}/${databaseProperties.name}"
            username = databaseProperties.username
            password = databaseProperties.password
            schema = databaseProperties.schema
        }
        return HikariDataSource(config)
    }

    /**
     * Configures and executes Flyway database migrations.
     *
     * This method:
     * - Logs the type of the provided [dataSource].
     * - Configures Flyway with `baselineOnMigrate(true)` to automatically baseline
     *   non-empty schemas without an existing Flyway metadata table.
     * - Triggers `migrate()` to apply all pending migrations.
     * - Logs the list of applied migrations in debug level.
     *
     * @param dataSource The migration [DataSource] used by Flyway to access the database.
     * @return The initialized [Flyway] instance after applying migrations.
     */
    @Bean
    fun flyway(dataSource: DataSource): Flyway {
        val logger = LoggerFactory.getLogger(FlywayConfig::class.java)
        logger.info("Flyway data source: {}", dataSource.javaClass.simpleName)

        val flyway = Flyway.configure()
            .dataSource(dataSource)
            .baselineOnMigrate(true)
            .load()

        flyway.migrate()

        logger.debug("=== Applied migrations ===")
        for (info in flyway.info().applied()) {
            logger.debug("{} - {}", info.version, info.description)
        }

        return flyway
    }
}
