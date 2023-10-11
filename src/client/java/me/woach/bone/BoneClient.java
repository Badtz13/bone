package me.woach.bone;

import me.woach.bone.blocks.BoneForgeEntityRenderer;
import me.woach.bone.networking.ClientPackets;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class BoneClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(Bone.BONE_FORGE_BLOCK_ENTITY, BoneForgeEntityRenderer::new);

        ClientPackets.RegisterServer2ClientPackets();
    }
}
