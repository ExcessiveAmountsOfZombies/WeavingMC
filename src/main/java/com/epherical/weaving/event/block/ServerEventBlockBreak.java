package com.epherical.weaving.event.block;

import com.epherical.weaving.event.Event;
import com.epherical.weaving.event.SubscriptionList;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ServerEventBlockBreak extends Event {
    private static final SubscriptionList subscribers = new SubscriptionList();

    private final ServerPlayerEntity playerEntity;
    private final BlockPos blockPosition;
    private final BlockState state;
    private final World world;


    public ServerEventBlockBreak(ServerPlayerEntity playerEntity, BlockPos blockPosition, BlockState state, World world) {
        this.playerEntity = playerEntity;
        this.blockPosition = blockPosition;
        this.state = state;
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getBlockPosition() {
        return blockPosition;
    }

    public ServerPlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public BlockState getState() {
        return state;
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
