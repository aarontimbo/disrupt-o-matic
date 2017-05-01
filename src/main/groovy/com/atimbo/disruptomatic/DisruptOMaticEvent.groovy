package com.atimbo.disruptomatic

import com.lmax.disruptor.EventFactory

final class DisruptOMaticEvent {
	
	String message

	final static EventFactory<DisruptOMaticEvent> EVENT_FACTORY = new EventFactory<DisruptOMaticEvent>() {
		DisruptOMaticEvent newInstance() {
			return new DisruptOMaticEvent()
		}
	}

	void set(String message) {
		this.message = message
	}
}