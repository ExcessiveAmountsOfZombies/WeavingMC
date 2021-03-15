package com.epherical.weaving.testing;

import com.epherical.weaving.event.ListenTo;
import com.epherical.weaving.event.Subscription;
import com.epherical.weaving.event.block.ServerEventBlockBreak;

public class BlockSubscriber implements Subscription {



    @ListenTo
    public void onBlockBreak(ServerEventBlockBreak eventBlockBreak) {

        System.out.println(eventBlockBreak.getBlockPosition());
    }
}
