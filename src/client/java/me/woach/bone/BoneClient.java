package me.woach.bone;

import me.woach.bone.blocks.BoneForgeEntityRenderer;
import me.woach.bone.networking.ClientPackets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class BoneClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(Bone.BONE_FORGE_BLOCK_ENTITY, BoneForgeEntityRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(Bone.BONE_FIRE_BLOCK, RenderLayer.getCutout());

        ClientPackets.RegisterServer2ClientPackets();
    }
}
