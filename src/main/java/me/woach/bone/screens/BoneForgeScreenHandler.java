package me.woach.bone.screens;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

public class BoneForgeScreenHandler extends AnvilScreenHandler {
    public BoneForgeScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(syncId, playerInventory, context);
    }
}
