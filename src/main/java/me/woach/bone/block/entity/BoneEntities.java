package me.woach.bone.block.entity;

import me.woach.bone.Bone;
import me.woach.bone.block.BoneBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BoneEntities {
    public static final BlockEntityType<BoneForgeBlockEntity> BONE_FORGE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Bone.getId("bone_forge_entity"),
                    FabricBlockEntityTypeBuilder.create(BoneForgeBlockEntity::new, BoneBlocks.BONE_FORGE_BLOCK).build());
}
