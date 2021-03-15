package com.epherical.weaving;

import com.epherical.weaving.event.Event;
import com.epherical.weaving.event.Subscription;
import com.epherical.weaving.internal.EventManager;
import com.epherical.weaving.testing.BlockSubscriber;
import net.fabricmc.api.DedicatedServerModInitializer;

public class Weaving implements DedicatedServerModInitializer {

    private static Weaving instance;

    @Override
    public void onInitializeServer() {
        instance = this;
        EventManager.addListener(new BlockSubscriber());


    }


    public <T extends Event> void callEvent(T event) {
        EventManager.callEvent(event);
    }

    public <T extends Subscription> void addEventListener(T subscriber) {
        EventManager.addListener(subscriber);
    }

    public static Weaving getInstance() {
        return instance;
    }
}
