package com.atimbo.disruptomatic

import com.lmax.disruptor.EventFactory

class DisruptOMaticEventFactory implements EventFactory<DisruptOMaticEvent> {

    DisruptOMaticEvent newInstance() {
        new DisruptOMaticEvent()
    }
}
