package me.woach.bone.mixin;

import me.woach.bone.block.BoneFireBlock;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFireBlock.class)
public class FirePowersBoneForge {
    @Inject(method = "onEntityCollision", at = @At("HEAD"))
    private void consumePowerUnderForge(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (entity.getType().equals(EntityType.ITEM)) {
            BoneFireBlock.essenceCollision(state,world,pos,entity);
        }
    }
}