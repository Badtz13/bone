package me.woach.bone.block.entity;

import me.woach.bone.Bone;
import me.woach.bone.block.BlocksRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.Arrays;
import java.util.function.Supplier;

public enum BlockEntityTypesRegistry {
    BONE_FORGE_BLOCK_ENTITY("bone_forge_entity", BoneForgeBlockEntity.class, BoneForgeBlockEntity::new,
            BlocksRegistry.BONE_FORGE_BLOCK);
    private final String path;
    private final Class<? extends BlockEntity> blockEntityClass;
    private final Supplier<BlockEntityType<? extends BlockEntity>> blockEntityTypeSupplier;
    private BlockEntityType<? extends BlockEntity> blockEntityType;

    BlockEntityTypesRegistry(String path, Class<? extends BlockEntity> blockEntityClass,
                             FabricBlockEntityTypeBuilder.Factory<? extends BlockEntity> blockEntitySupplier, BlocksRegistry... blockRegistryArray) {
        this.path = path;
        this.blockEntityClass = blockEntityClass;
        this.blockEntityTypeSupplier = () -> FabricBlockEntityTypeBuilder.create(blockEntitySupplier, Arrays.stream(blockRegistryArray)
                .map(BlocksRegistry::get).toArray(Block[]::new)).build(null);
    }

    public static void registerAll() {
        for (BlockEntityTypesRegistry value : values()) {
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Bone.getId(value.path), value.get());
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends BlockEntity> BlockEntityType<T> get() {
        return (BlockEntityType<T>) get(blockEntityClass);
    }

    @SuppressWarnings({"unchecked","unused"})
    private <T extends BlockEntity> BlockEntityType<T> get(Class<T> clazz) {
        if (blockEntityType == null) {
            blockEntityType = blockEntityTypeSupplier.get();
        }

        return (BlockEntityType<T>) blockEntityType;
    }

}
