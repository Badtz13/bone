package me.woach.bone;

import me.woach.bone.block.BoneBlocks;
import me.woach.bone.block.entity.BoneEntities;
import me.woach.bone.items.BoneItemGroup;
import me.woach.bone.items.BoneItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.woach.bone.datapack.BoneReloader;
import me.woach.bone.effects.BoneEffect;
import me.woach.bone.effects.Hog;
import me.woach.bone.effects.SwiftStream;
import net.minecraft.registry.RegistryKey;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Bone implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Bone");

    public static Identifier getId(String path) {
            return new Identifier("bone", path);
    }

    public static final RegistryKey<Registry<BoneEffect>> BONE_EFFECT_REGISTRY_KEY = RegistryKey
                    .ofRegistry(getId("bone_effect_registry"));
    public static final Registry<BoneEffect> BONE_EFFECT_REGISTRY = FabricRegistryBuilder
                    .createSimple(BONE_EFFECT_REGISTRY_KEY).buildAndRegister();
    @SuppressWarnings("InstantiationOfUtilityClass")
    @Override
    public void onInitialize() {
        new BoneItems();
        new BoneBlocks();
        new BoneEntities();
        BoneItemGroup.registerItemGroup();

        Registry.register(BONE_EFFECT_REGISTRY, getId("hog"), new Hog());
        Registry.register(BONE_EFFECT_REGISTRY, getId("swift_stream"), new SwiftStream());

        BoneReloader reloader = new BoneReloader();
        reloader.registerReloadListener();
    }
}