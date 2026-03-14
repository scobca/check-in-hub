package org.scobca.checkinhub.utils

import kotlinx.serialization.json.Json
import org.scobca.checkinhub.dto.messages.RecordsUpdateMessage
import org.scobca.checkinhub.enums.Attendance
import org.scobca.checkinhub.service.UpdatesNotificationService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class ReactiveNotificationsHandler(
    private val json: Json,
    private val updatesNotificationService: UpdatesNotificationService,
) : WebSocketHandler {

    override fun handle(session: WebSocketSession): Mono<Void> {
        val input = session.receive()
            .map { it.payloadAsText }
            .map { json.decodeFromString<String>(it) }
            .doOnNext { updatesNotificationService.sendMessage(RecordsUpdateMessage(1, Attendance.NOT_STATED)) }  // ← РЕАКТИВНО!
            .then()

        val  output =updatesNotificationService.getMessages()
            .map { msg ->
                session.textMessage(json.encodeToString(msg))
            }
            .let(session::send)

        return input.and(output)
    }
}