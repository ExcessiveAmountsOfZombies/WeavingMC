package com.epherical.weaving.testing;

import com.epherical.weaving.event.ListenTo;
import com.epherical.weaving.event.Subscription;
import com.epherical.weaving.event.block.ServerEventBlockBreak;
import com.epherical.weaving.event.block.ServerEventBlockPlace;

public class BlockSubscriber implements Subscription {



    @ListenTo
    public void onBlockBreak(ServerEventBlockBreak eventBlockBreak) {
        System.out.println(eventBlockBreak.getBlockPosition());
    }

    @ListenTo
    public void onBlockPlace(ServerEventBlockPlace event) {
        event.setCancelled(true);
        System.out.println(event.getPlacementContext().getBlockPos());
        System.out.println(event.getBlockState());
    }
}
