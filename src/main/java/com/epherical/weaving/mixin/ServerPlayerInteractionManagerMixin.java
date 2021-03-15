package com.epherical.weaving.mixin;

import com.epherical.weaving.event.block.ServerEventBlockBreak;
import com.epherical.weaving.internal.EventManager;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {


    @Shadow public ServerPlayerEntity player;

    @Shadow public ServerWorld world;

    @Inject(method = "tryBreakBlock",
            at = {@At("HEAD")},
            slice = @Slice(
                    from = @At(
                            value = "INVOKE_ASSIGN",
                            target = "Lnet/minecraft/server/world/ServerWorld;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"
                    ),
                    to = @At(
                            value = "INVOKE_ASSIGN",
                            target = "Lnet/minecraft/server/world/ServerWorld;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"
                    )
            ),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            cancellable = true)
    public void onBlockBreak(BlockPos pos, CallbackInfoReturnable<Boolean> cir, BlockState blockState) {
        ServerEventBlockBreak blockEventBreak = new ServerEventBlockBreak(player, pos, blockState, world);
        EventManager.callEvent(blockEventBreak);
        if (blockEventBreak.isCancelled()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
