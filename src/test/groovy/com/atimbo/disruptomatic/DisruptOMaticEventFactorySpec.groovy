package com.atimbo.disruptomatic

import spock.lang.Specification

class DisruptOMaticEventFactorySpec extends Specification {

    DisruptOMaticEventFactory eventFactory

    void setup() {
        eventFactory = new DisruptOMaticEventFactory()
    }

    void 'create an instance of a disrupt-0-matic event'() {
        setup:
        DisruptOMaticEvent event = eventFactory.newInstance()

        expect:
        event
    }
}
