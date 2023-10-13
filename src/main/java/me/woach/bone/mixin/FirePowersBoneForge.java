package me.woach.bone.mixin;

import me.woach.bone.Bone;
import me.woach.bone.blocks.BoneForgeBlockEntity;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFireBlock.class)
public class FirePowersBoneForge {
    @Unique
    private BoneForgeBlockEntity forge;
    @Unique
    private ItemStack essence;

    @Unique
    private boolean checkConsumptionParameters(World world, Entity entity, BlockPos pos) {
        BlockEntity be = world.getBlockEntity(pos.up());
        if (be == null || !be.getType().equals(Bone.BONE_FORGE_BLOCK_ENTITY))
            return false;
        forge = (BoneForgeBlockEntity) be;
        if (!entity.getType().equals(EntityType.ITEM))
            return false;
        ItemEntity item = (ItemEntity) entity;
        essence = item.getStack();
        return forge.isDust(essence);
    }
    @Inject(method = "onEntityCollision", at = @At("HEAD"))
    private void consumePowerUnderForge(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if(checkConsumptionParameters(world, entity, pos)) {
            forge.setEssenceLevel(essence);
            entity.discard();
        }
    }

    @Inject(method = "onBreak", at = @At("HEAD"))
    private void resetBoneForgeLevel(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {
        BlockEntity be = world.getBlockEntity(pos.up());
        if (be != null && be.getType().equals(Bone.BONE_FORGE_BLOCK_ENTITY)) {
            forge = (BoneForgeBlockEntity) be;
            forge.resetEssenceLevel();
        }
    }
}
