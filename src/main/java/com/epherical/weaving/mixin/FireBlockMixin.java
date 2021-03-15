package com.epherical.weaving.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(FireBlock.class)
public class FireBlockMixin {


    @Inject(method = "trySpreadingFire",
            at = {@At("HEAD")},
            slice = @Slice(
                    from = @At(
                            value = "INVOKE_ASSIGN",
                            target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
                            ordinal = 1
                    ),
                    to = @At(
                            value = "INVOKE_ASSIGN",
                            target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
                            ordinal = 1
                    )
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT,
            cancellable = true)
    public void onFireSpread(World world, BlockPos pos, int spreadFactor, Random rand, int currentAge, CallbackInfo ci, BlockState state) {
        /*if (ServerBlockEvents.BLOCK_BURN.invoker().onBurn(world, pos, state)) {
            ci.cancel();
        }*/

    }
}
