package com.epherical.weaving.event;

/**
 * Abstract Event class, by default an event can not be cancelled, but if you want to cancel it you have to override
 * canBeCancelled() to true and then the events can be cancelled.
 */
public abstract class Event {

    protected boolean cancelled = false;

    public abstract SubscriptionList getSubscriptions();

    public boolean canBeCancelled() {
        return false;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        if (canBeCancelled()) {
            this.cancelled = cancelled;
        } else {
            throw new RuntimeException("Class must override canBeCancelled before attempting to cancel an event.");
        }
    }
}
