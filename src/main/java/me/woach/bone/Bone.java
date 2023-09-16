package me.woach.bone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.woach.bone.blocks.BoneForgeBlock;
import me.woach.bone.items.GenericBone;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class Bone implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Bone");

    public static Identifier getId(String path) {
        return new Identifier("bone", path);
    }

    public static final Registry<GenericBone> BONE_REGISTRY = FabricRegistryBuilder
            .createSimple(RegistryKey.ofRegistry(new Identifier("bone", "bone_registry")))
            .buildAndRegister();

    public static final BoneForgeBlock BONE_FORGE_BLOCK = new BoneForgeBlock();

    @Override
    public void onInitialize() {
        Registry.register(Registries.BLOCK, getId("bone_forge"), BONE_FORGE_BLOCK);
        Registry.register(Registries.ITEM, getId("bone_forge"),
                new BlockItem(BONE_FORGE_BLOCK, new FabricItemSettings()));
    }
}
