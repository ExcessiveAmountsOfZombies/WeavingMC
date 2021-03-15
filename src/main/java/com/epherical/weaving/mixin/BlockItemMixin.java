package com.epherical.weaving.mixin;

import com.epherical.weaving.event.block.ServerEventBlockPlace;
import com.epherical.weaving.internal.EventManager;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Inject(method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;",
            at = {@At("HEAD")},
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = ""
                    )/*,
                    to = @At(
                            value = "INVOKE_ASSIGN",
                            target = "Lnet/minecraft/item/BlockItem;getPlacementState(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/block/BlockState;",
                            ordinal = 1
                    )*/
            ),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            cancellable = true)
    public void place(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> cir, BlockState blockState) {
        if (blockState == null) {
            cir.setReturnValue(ActionResult.FAIL);
        }
        if (!context.getWorld().isClient) {
            ServerEventBlockPlace place = new ServerEventBlockPlace(context, blockState);
            EventManager.callEvent(place);
            if (place.isCancelled()) {
                cir.setReturnValue(ActionResult.FAIL);
                cir.cancel();
            }
        }
    }
}
