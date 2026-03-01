package org.scobca.checkinhub.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * Spring configuration class for R2DBC reactive transaction management.
 *
 * Enables reactive transaction management with `@EnableTransactionManagement` and provides
 * a [ReactiveTransactionManager] bean that wraps the [ConnectionFactory] for declarative
 * transaction handling in reactive flows using annotations like `@Transactional`.
 */
@Configuration
@EnableTransactionManagement
class R2dbcConfig {

    /**
     * Creates a reactive transaction manager for R2DBC operations.
     *
     * This manager integrates with Spring's reactive transaction abstraction, enabling
     * declarative transaction control (`@Transactional`) across reactive database operations.
     *
     * @param connectionFactory The R2DBC [ConnectionFactory] used for database connections.
     * @return A configured [ReactiveTransactionManager] instance.
     */
    @Bean
    fun transactionManager(connectionFactory: ConnectionFactory): ReactiveTransactionManager {
        return R2dbcTransactionManager(connectionFactory)
    }
}