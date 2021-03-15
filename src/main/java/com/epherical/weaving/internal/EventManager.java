package com.epherical.weaving.internal;

import com.epherical.weaving.event.Subscription;
import com.epherical.weaving.event.Event;
import com.epherical.weaving.event.ListenTo;
import com.epherical.weaving.event.Subscriber;
import com.epherical.weaving.event.SubscriptionList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Based on lahwrans fast events
 * https://github.com/lahwran/fastevents
 * Bukkit actually uses the same base code and then modifies it a bunch, so here is my version, modified of course.
 */
public class EventManager {

    /**
     * Call an event.
     *
     * @param <T> Event subclass
     * @param event Event to handle
     */
    public static <T extends Event> void callEvent(T event) {
        SubscriptionList subscriptionList = event.getSubscriptions();
        subscriptionList.bake();

        Subscriber[] listeners = subscriptionList.getSubscriptions();

        for (Subscriber subscriber : listeners) {
            try {
                subscriber.callEvent(event);
            } catch (Throwable t) {
                // todo: no sout error
                System.err.println("Error while passing event " + event);
                t.printStackTrace();
            }
        }
    }

    public static <T extends Subscription> void addListener(T listener) {
        Class<?> clazz = listener.getClass();
        try {
            for (Method method : clazz.getDeclaredMethods()) {
                ListenTo to = method.getAnnotation(ListenTo.class);
                if (to != null) {
                    // now we need to make sure it has the proper parameter.
                    if (method.getParameterTypes().length != 1) {
                        continue;
                    }
                    Class<?> parameter = method.getParameterTypes()[0];
                    Method listeners = parameter.getDeclaredMethod("getSubscribers");
                    listeners.setAccessible(true);
                    SubscriptionList list = (SubscriptionList) listeners.invoke(null);

                    list.register(new Subscriber(listener, to.order(), method, to.listenToCancelledEvents()));

                    if (Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                        method.setAccessible(true);

                    }
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            // todo: fix bad error
            throw new Error("getscubscriubers does not exist");
        }
    }
}
