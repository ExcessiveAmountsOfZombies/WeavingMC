package com.epherical.weaving.event;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Subscriber {

    private final Subscription subscription;
    private final EventOrder eventOrder;
    private final Method eventMethod;
    private final boolean listenToCancelledEvents;


    public Subscriber(Subscription subscription, EventOrder eventOrder, Method eventMethod, boolean listenToCancelledEvents) {
        this.subscription = subscription;
        this.eventOrder = eventOrder;
        this.eventMethod = eventMethod;
        this.listenToCancelledEvents = listenToCancelledEvents;
    }

    public void callEvent(Event event) {
        try {
            if (event.isCancelled() && !isListeningToCancelledEvents()) {
                return;
            }
            eventMethod.invoke(subscription, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public boolean isListeningToCancelledEvents() {
        return listenToCancelledEvents;
    }

    public EventOrder getOrder() {
        return eventOrder;
    }

    public Subscription getSubscription() {
        return subscription;
    }
}
