package com.atimbo.disruptomatic

import com.lmax.disruptor.RingBuffer
import com.lmax.disruptor.dsl.Disruptor
import groovy.util.logging.Slf4j
import org.codehaus.groovy.runtime.InvokerHelper

import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Slf4j
class DisruptOMaticApp extends Script {

	def run() {
		// Executor that will be used to spin up new threads for consumers
		Executor executor = Executors.newCachedThreadPool()

		// The factory for the event
		DisruptOMaticEventFactory eventFactory = new DisruptOMaticEventFactory()

		// Specify the size of the ring buffer, power of 2
		int bufferSize = 64

		// Construct the disruptor
		Disruptor disruptor = new Disruptor<>(eventFactory, bufferSize, executor)

		// Connect the handler
		disruptor.handleEventsWith(new DisruptOMaticEventHandler())

		log.info 'Starting the disruptor...'
		disruptor.start()

		// Get the ring buffer from the disruptor to be used for publishing
		RingBuffer<DisruptOMaticEvent> ringBuffer = disruptor.ringBuffer

		// Create the producer
		DisruptOMaticEventProducer eventProducer = new DisruptOMaticEventProducer(ringBuffer)

		String[] args = this.binding.getVariable('args')
		log.info "Found args: $args"
		if (args) {
			log.info "Producing messages from args: $args"
			args.each { arg ->
				eventProducer.onData(arg)
//				Thread.sleep(1000)
			}
		} else {
			log.info "No messages. Non, Niet!"
		}

		log.info '***DONE***'
		disruptor.shutdown()
		System.exit(0)
	}

	static void main(String[] args) {
		InvokerHelper.runScript(DisruptOMaticApp, args)
	}	
}
