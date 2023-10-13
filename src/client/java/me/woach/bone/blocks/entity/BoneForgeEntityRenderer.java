package me.woach.bone.blocks.entity;

import me.woach.bone.block.entity.BoneForgeBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class BoneForgeEntityRenderer implements BlockEntityRenderer<BoneForgeBlockEntity> {

    public BoneForgeEntityRenderer(BlockEntityRendererFactory.Context ignoredContext) {

    }

    @Override
    public void render(BoneForgeBlockEntity entity, float tickDelta,
            MatrixStack matrices, VertexConsumerProvider vertexConsumers,
            int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        ItemStack item = entity.getRenderStack();

        matrices.push();
        matrices.translate(0.5f, 0.78f, 0.5f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));

        itemRenderer.renderItem(item, ModelTransformationMode.GUI,
                getLightLevel(Objects.requireNonNull(entity.getWorld()), entity.getPos()),
                overlay, matrices, vertexConsumers,
                entity.getWorld(), 0);
        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos) {
        assert world != null;
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }

}
