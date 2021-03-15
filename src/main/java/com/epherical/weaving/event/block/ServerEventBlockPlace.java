package com.epherical.weaving.event.block;

import com.epherical.weaving.event.Event;
import com.epherical.weaving.event.SubscriptionList;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;

public class ServerEventBlockPlace extends Event {
    private static final SubscriptionList subscribers = new SubscriptionList();

    private final ItemPlacementContext placementContext;
    private final BlockState blockState;


    public ServerEventBlockPlace(ItemPlacementContext placementContext, BlockState blockState) {
        this.placementContext = placementContext;
        this.blockState = blockState;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public ItemPlacementContext getPlacementContext() {
        return placementContext;
    }

    @Override
    public SubscriptionList getSubscriptions() {
        return subscribers;
    }

    @Override
    public boolean canBeCancelled() {
        return true;
    }


    public static SubscriptionList getSubscribers() {
        return subscribers;
    }
}
