package org.scobca.checkinhub.config

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.ConnectionFactoryOptions.DATABASE
import io.r2dbc.spi.ConnectionFactoryOptions.DRIVER
import io.r2dbc.spi.ConnectionFactoryOptions.HOST
import io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD
import io.r2dbc.spi.ConnectionFactoryOptions.PORT
import io.r2dbc.spi.ConnectionFactoryOptions.USER
import io.r2dbc.spi.Option
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Spring configuration class responsible for setting up the reactive PostgreSQL database connection.
 *
 * This configuration defines a bean for [ConnectionFactory], which is used by R2DBC to establish
 * non-blocking connections to the PostgreSQL database using the provided [DatabaseProperties].
 *
 * @property databaseProperties The properties containing database connection parameters such as host, port, name, username, password, and schema.
 */
@Configuration
class DatabaseConfig(private val databaseProperties: DatabaseProperties) {

    /**
     * Creates and configures a reactive [ConnectionFactory] for connecting to a PostgreSQL database.
     *
     * The connection parameters (driver, host, port, name, user credentials, and schema) are taken
     * from the [DatabaseProperties] configuration.
     *
     * @return A fully built [ConnectionFactory] instance for PostgreSQL connections.
     */
    @Bean
    fun connectionFactory(): ConnectionFactory {
        val options = ConnectionFactoryOptions.builder()
            .option(DRIVER, "postgresql")
            .option(HOST, databaseProperties.host)
            .option(PORT, databaseProperties.port.toInt())
            .option(DATABASE, databaseProperties.name)
            .option(USER, databaseProperties.username)
            .option(PASSWORD, databaseProperties.password)
            .option(Option.valueOf("schema"), databaseProperties.schema)
            .build()

        return ConnectionFactories.get(options)
    }
}