package com.atimbo.disruptomatic

import com.lmax.disruptor.EventTranslatorOneArg
import com.lmax.disruptor.RingBuffer

/**
 * Producer with Event Translator
 */
class DisruptOMaticEventProducer {

    private final RingBuffer<DisruptOMaticEvent> ringBuffer

    DisruptOMaticEventProducer(RingBuffer<DisruptOMaticEvent> ringBuffer) {
        this.ringBuffer = ringBuffer
    }

    private static final EventTranslatorOneArg<DisruptOMaticEvent, String> TRANSLATOR = setupTranslator()

    void onData(String msg) {
        ringBuffer.publishEvent(TRANSLATOR, msg)
    }

    private static EventTranslatorOneArg<DisruptOMaticEvent, String> setupTranslator() {
        new EventTranslatorOneArg<DisruptOMaticEvent, String>() {
            @Override
            void translateTo(DisruptOMaticEvent event, long sequence, String msg) {
                event.set(msg)
            }
        }
    }
}
