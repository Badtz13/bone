package me.woach.bone.block;

import java.util.function.Supplier;

import me.woach.bone.Bone;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public enum BlocksRegistry {
    BONE_FORGE_BLOCK("bone_forge", BoneForgeBlock::new),
    BONE_FIRE_BLOCK("bone_fire", BoneFireBlock::new),
    FIREWOOD_BLOCK("firewood_block", () -> new Block(
            FabricBlockSettings.create().strength(1.5f).sounds(BlockSoundGroup.WOOD).burnable()));

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
