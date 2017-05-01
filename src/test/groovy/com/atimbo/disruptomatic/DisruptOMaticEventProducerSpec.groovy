package com.atimbo.disruptomatic

import com.lmax.disruptor.RingBuffer
import com.lmax.disruptor.dsl.Disruptor
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DisruptOMaticEventProducerSpec extends Specification {

    @Shared
    DisruptOMaticEventProducer eventProducer

    @Shared
    Disruptor<DisruptOMaticEvent> disruptor

    void setupSpec() {
        // Executor that will be used to spin up new threads for consumers
        Executor executor = Executors.newCachedThreadPool()

        // The factory for the event
        DisruptOMaticEventFactory eventFactory = new DisruptOMaticEventFactory()

        // Specify the size of the ring buffer, power of 2
        int bufferSize = 64

        // Construct the disruptor
        disruptor = new Disruptor<>(eventFactory, bufferSize, executor)

        // Connect the handler
        disruptor.handleEventsWith(new DisruptOMaticEventHandler())

        disruptor.start()

        // Get the ring buffer from the disruptor to be used for publishing
        RingBuffer<DisruptOMaticEvent> ringBuffer = disruptor.ringBuffer

        // Create the producer
        eventProducer = new DisruptOMaticEventProducer(ringBuffer)
    }

    void cleanupSpec() {
        disruptor.shutdown()
    }

    void 'produce disrupt-o-matic events'() {
        given:
        List<String> messages = ['foo', 'bar', 'hufflepuff', 'gryffindor', 'slytherin']

        when:
        messages.each { msg ->
            eventProducer.onData(msg)
        }

        then:
        noExceptionThrown()
    }
}
