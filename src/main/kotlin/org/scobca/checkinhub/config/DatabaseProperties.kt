package org.scobca.checkinhub.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * Configuration properties for the database connection.
 *
 * This class is automatically populated from the application's configuration file using the prefix `database`.
 * It holds all essential parameters needed to establish a connection with the PostgreSQL database.
 */
@Configuration
@ConfigurationProperties(prefix = "database")
class DatabaseProperties {

    /** The hostname or IP address of the database server. */
    lateinit var host: String

    /** The port number on which the database server listens. */
    lateinit var port: String

    /** The name of the target database. */
    lateinit var name: String


    /** The username used for authenticating the database connection. */
    lateinit var username: String

    /** The password used for authenticating the database connection. */
    lateinit var password: String

    /** The default schema to be used in the database connection. */
    lateinit var schema: String
}