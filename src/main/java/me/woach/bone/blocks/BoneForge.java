package me.woach.bone.blocks;

import me.woach.bone.screens.BoneForgeScreenHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BoneForge extends Block {
    public BoneForge() {
        super(FabricBlockSettings.create().strength(4.0f).requiresTool().luminance(8).sounds(BlockSoundGroup.METAL));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
            BlockHitResult hit) {
        if (!world.isClient) {
            player.openHandledScreen(new NamedScreenHandlerFactory() {
                @Override
                public Text getDisplayName() {
                    return Text.of("Bone Forge");
                }

                @Override
                public BoneForgeScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                    return new BoneForgeScreenHandler(syncId, inv, ScreenHandlerContext.create(world, pos));
                }
            });
        }

        return ActionResult.SUCCESS;
    }
}
