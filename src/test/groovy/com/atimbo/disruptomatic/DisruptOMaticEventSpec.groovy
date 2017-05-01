package com.atimbo.disruptomatic

import spock.lang.Specification

class DisruptOMaticEventSpec extends Specification {
	
	void 'create an disrupt-o-matic event'() {
		setup:
		def event = new DisruptOMaticEvent()

		expect:
		event
	}
}