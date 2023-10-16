package me.woach.bone.block;

import me.woach.bone.Bone;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Supplier;

public enum BlocksRegistry {
    BONE_FORGE_BLOCK("bone_forge", BoneForgeBlock::new),
    BONE_FIRE_BLOCK("bone_fire", BoneFireBlock::new),
    CHARCOAL_BLOCK("charcoal_block", () -> new Block(
            FabricBlockSettings.create().requiresTool().strength(3.0f).sounds(BlockSoundGroup.STONE).burnable()));

    private final String path;
    private final Supplier<Block> blockSupplier;
    private Block block;

    BlocksRegistry(String path, Supplier<Block> blockSupplier) {
        this.path = path;
        this.blockSupplier = blockSupplier;
    }

    public static void registerAll() {
        for (BlocksRegistry value : values()) {
            Registry.register(Registries.BLOCK, Bone.getId(value.path), value.get());
        }
    }

    public Block get() {
        if (block == null) {
            block = blockSupplier.get();
        }

        return block;
    }
}
