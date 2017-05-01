package com.atimbo.disruptomatic

import com.lmax.disruptor.EventHandler
import groovy.util.logging.Slf4j

@Slf4j
class DisruptOMaticEventHandler implements EventHandler<DisruptOMaticEvent> {

    private static final int MAX_EVENT_BUFFER_SIZE = 50
    List<DisruptOMaticEvent> eventBuffer = []

    void onEvent(DisruptOMaticEvent event, long sequence, boolean endOfBatch) {
        eventBuffer.add(event)
        log.info '+++ Adding Disrupt-o-matic Event to event buffer batch' +
                "[sequence: $sequence, endOfBatch: $endOfBatch, message: $event.message]"
        if (!endOfBatch && eventBuffer.size() < MAX_EVENT_BUFFER_SIZE) {
            return
        }

        log.info '>>> Handling Disrupt-o-matic Event ' +
                 "[sequence: $sequence, endOfBatch: $endOfBatch, messages: ${eventBuffer*.message}]"
        eventBuffer.clear()
    }
}
