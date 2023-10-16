package me.woach.bone.mixin;

import me.woach.bone.block.BlocksRegistry;
import me.woach.bone.block.BoneFireBlock;
import me.woach.bone.block.entity.BlockEntityTypesRegistry;
import me.woach.bone.block.entity.BoneForgeBlockEntity;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
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
            ItemStack essence = ((ItemEntity) entity).getStack();
            if(BoneForgeBlockEntity.canFuelBoneforge(essence)) {
                // Update bone fire type
                world.setBlockState(pos,
                        ((BoneFireBlock) BlocksRegistry.BONE_FIRE_BLOCK.get()).getEssenceState(essence));
                entity.discard();

                // Update bone forge to match
                BlockEntity aboveFire = world.getBlockEntity(pos.up());
                if (aboveFire != null && aboveFire.getType().equals(BlockEntityTypesRegistry.BONE_FORGE_BLOCK_ENTITY.get())) {
                    BoneForgeBlockEntity forge = (BoneForgeBlockEntity) aboveFire;

                    // If the forging works, then convert the BoneFireBlock to a regular fire
                    if (forge.attemptToForge(world, pos.up()))
                        BoneFireBlock.convertToFire(world, pos);
                }
            }
        }
    }

//    @Inject(method = "onBreak", at = @At("HEAD"))
//    private void resetBoneForgeLevel(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {
//        // Reset bone forge if present
//        BlockEntity aboveFire = world.getBlockEntity(pos.up());
//        if (aboveFire != null && aboveFire.getType().equals(BlockEntityTypesRegistry.BONE_FORGE_BLOCK_ENTITY.get())) {
//            BoneForgeBlockEntity forge = (BoneForgeBlockEntity) aboveFire;
//        }
//    }
}