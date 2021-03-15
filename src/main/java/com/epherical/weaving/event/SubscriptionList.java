package com.epherical.weaving.event;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

/**
 * Based on lahwrans fast events
 * https://github.com/lahwran/fastevents
 *
 */
public class SubscriptionList {
    /*
    Used to check if the current SubscriberList has been baked or not.
    Is set to false whenever a new event is registered.
     */
    private boolean baked = false;

    /*
    The array of subscriptions, is modified every time bake() is called.
     */
    Subscriber[] subscriptions;

    private final EnumMap<EventOrder, ArrayList<Subscriber>> subscriptionSlots;

    /*
    List of all the subscriptions that were baked
     */
    private static ArrayList<SubscriptionList> allLists = new ArrayList<>();

    public static void bakeAll() {
        for (SubscriptionList h : allLists) {
            h.bake();
        }
    }

    public SubscriptionList() {
        subscriptionSlots = new EnumMap<>(EventOrder.class);
        for (EventOrder o : EventOrder.values()) {
            subscriptionSlots.put(o, new ArrayList<>());
        }
        allLists.add(this);
    }

    /**
     * Register a new {@link Subscriber} in this {@link SubscriptionList}
     * @param subscriber the {@link Subscriber} that will be added to the handler
     */
    public void register(Subscriber subscriber) {
        if (subscriptionSlots.get(subscriber.getOrder()).contains(subscriber))
            throw new IllegalStateException("This subscription is already registered to order "+ subscriber.getOrder().toString());
        baked = false;
        subscriptionSlots.get(subscriber.getOrder()).add(subscriber);
    }

    /**
     * Remove a {@link Subscriber} from all order slots
     * @param subscriber subscriber to purge
     */
    public void unregister(Subscriber subscriber) {
        for (EventOrder o : EventOrder.values()) {
            unregister(subscriber, o);
        }
    }

    /**
     * Remove a {@link Subscriber} from a specific order slot
     * @param subscriber subscriber to remove
     * @param eventOrder order from which to remove subscription
     */
    public void unregister(Subscriber subscriber, EventOrder eventOrder) {
        if (subscriptionSlots.get(eventOrder).contains(subscriber)) {
            baked = false;
            subscriptionSlots.get(eventOrder).remove(subscriber);
        }
    }

    public void bake() {
        if (baked)
            return; // don't re-bake when still valid

        ArrayList<Subscriber> subscriptionList = new ArrayList<>();
        for (Map.Entry<EventOrder, ArrayList<Subscriber>> entry : subscriptionSlots.entrySet()) {
            subscriptionList.addAll(entry.getValue());
        }

        subscriptions = subscriptionList.toArray(new Subscriber[0]);
        baked = true;
    }

    public Subscriber[] getSubscriptions() {
        return subscriptions;
    }
}
