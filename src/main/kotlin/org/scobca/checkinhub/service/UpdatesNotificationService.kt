package org.scobca.checkinhub.service

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.reactor.asFlux
import org.scobca.checkinhub.dto.messages.RecordsUpdateMessage
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class UpdatesNotificationService {
    private val messages = MutableSharedFlow<RecordsUpdateMessage>(extraBufferCapacity = 1000)

    fun sendMessage(content: RecordsUpdateMessage) {
        messages.tryEmit(content)
    }

    fun getMessages(): Flux<RecordsUpdateMessage> = messages.asFlux()
}

