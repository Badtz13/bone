package me.woach.bone.block;

import me.woach.bone.Bone;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BoneBlocks {
    public static final Block BONE_FORGE_BLOCK = register("bone_forge", new BoneForgeBlock());

    public static Block register(String path, Block block) {
        return Registry.register(Registries.BLOCK, Bone.getId(path), block);
    }
}
