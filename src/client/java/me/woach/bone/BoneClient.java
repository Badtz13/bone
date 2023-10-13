package me.woach.bone;

import me.woach.bone.block.entity.BoneEntities;
import me.woach.bone.blocks.entity.BoneForgeEntityRenderer;
import me.woach.bone.networking.ClientPackets;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class BoneClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(BoneEntities.BONE_FORGE_BLOCK_ENTITY, BoneForgeEntityRenderer::new);

        ClientPackets.RegisterServer2ClientPackets();
    }
}
