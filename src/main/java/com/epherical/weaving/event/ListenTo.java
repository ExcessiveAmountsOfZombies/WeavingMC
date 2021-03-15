package com.epherical.weaving.event;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListenTo {

    /**
     * @return The {@link EventOrder} that the event will play in, defaults to FOURTH to allow other events to take precedence.
     */
    EventOrder order() default EventOrder.FOURTH;


    /**
     *
     * If listenToCancelledEvents is set to true it will listen for events that have been cancelled, defaults to false.
     * @return returns whether or not if a cancelled event will be listened to by this listener.
     */
    boolean listenToCancelledEvents() default false;


}
