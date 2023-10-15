package me.woach.bone.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BoneForgeBlock extends Block implements BlockEntityProvider {
    public BoneForgeBlock() {
        super(FabricBlockSettings.create().pistonBehavior(PistonBehavior.BLOCK).strength(4.0f).requiresTool()
                .luminance(8).sounds(BlockSoundGroup.STONE));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(
                VoxelShapes.cuboid(0.00f, 0.375f, 0.00f, 1.00f, 0.75f, 1.00f));
    }

    // public static boolean isAlit(WorldAccess world, BlockPos pos) {
    // BlockPos below = pos.down();
    // }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
            BlockHitResult hit) {
        if (world.isClient)
            return ActionResult.SUCCESS;
        BoneForgeBlockEntity blockEntity = (BoneForgeBlockEntity) world.getBlockEntity(pos);
        assert blockEntity != null;

        ItemStack playersItem = player.getStackInHand(hand);
        if (!playersItem.isEmpty()) {
            // Try to put tool into forge
            if (blockEntity.isBoneforgable(playersItem)
                    && blockEntity.getStack(BoneForgeBlockEntity.TOOL_SLOT).isEmpty()) {
                blockEntity.setStack(BoneForgeBlockEntity.TOOL_SLOT, playersItem.copy());
                playersItem.setCount(0);
                return ActionResult.SUCCESS;
            }

            // Try to put bone into forge
            if (blockEntity.isBone(playersItem) && blockEntity.getStack(BoneForgeBlockEntity.BONE_SLOT).isEmpty()) {
                blockEntity.setStack(BoneForgeBlockEntity.BONE_SLOT, playersItem.copyWithCount(1));
                if (!player.getAbilities().creativeMode) {
                    playersItem.decrement(1);
                }
                return ActionResult.SUCCESS;
            }
        } else {
            // Try to remove bone first
            ItemStack blockItem = blockEntity.getStack(BoneForgeBlockEntity.BONE_SLOT);
            if (!blockItem.isEmpty()) {
                player.getInventory().offerOrDrop(blockItem);
                blockEntity.removeStack(BoneForgeBlockEntity.BONE_SLOT);
                return ActionResult.SUCCESS;
            }

            // Then try to remove tool
            blockItem = blockEntity.getStack(BoneForgeBlockEntity.TOOL_SLOT);
            if (!blockItem.isEmpty()) {
                player.getInventory().offerOrDrop(blockItem);
                blockEntity.removeStack(BoneForgeBlockEntity.TOOL_SLOT);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BoneForgeBlockEntity(pos, state);
    }
}
