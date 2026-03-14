package org.scobca.checkinhub.config

import org.scobca.checkinhub.utils.ReactiveNotificationsHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter

@Configuration
@EnableWebFlux
class WebFluxConfig : WebFluxConfigurer {

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        configurer.addCustomResolver(ReactivePageableHandlerMethodArgumentResolver())
    }

    @Bean
    fun handlerMapping(handler: ReactiveNotificationsHandler): HandlerMapping {
        val mapping = SimpleUrlHandlerMapping(mapOf("/ws/notifications" to handler), -1)

        return mapping
    }

    @Bean
    fun webSocketHandlerAdapter() = WebSocketHandlerAdapter()
}