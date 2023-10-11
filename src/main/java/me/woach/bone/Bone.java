package me.woach.bone;

import me.woach.bone.blocks.BoneForgeBlockEntity;
import me.woach.bone.datapack.BoneReloader;
import me.woach.bone.effects.BoneEffect;
import me.woach.bone.effects.Hog;
import me.woach.bone.effects.SwiftStream;
import me.woach.bone.items.BoneItem;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.RegistryKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.woach.bone.blocks.BoneForgeBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Bone implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Bone");

    public static Identifier getId(String path) {
        return new Identifier("bone", path);
    }

    public static final RegistryKey<Registry<BoneEffect>> BONE_EFFECT_REGISTRY_KEY =
            RegistryKey.ofRegistry(getId("bone_effect_registry"));
    public static final Registry<BoneEffect> BONE_EFFECT_REGISTRY =
            FabricRegistryBuilder.createSimple(BONE_EFFECT_REGISTRY_KEY).buildAndRegister();

    public static final BoneForgeBlock BONE_FORGE_BLOCK = new BoneForgeBlock();
    public static final BlockEntityType<BoneForgeBlockEntity> BONE_FORGE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            getId("bone_forge_entity"),
            FabricBlockEntityTypeBuilder.create(BoneForgeBlockEntity::new, BONE_FORGE_BLOCK).build()
    );
    public static final BoneItem BONE_ITEM = new BoneItem();

    @Override
    public void onInitialize() {
        Registry.register(Registries.BLOCK, getId("bone_forge"), BONE_FORGE_BLOCK);
        Registry.register(Registries.ITEM, getId("bone_forge"),
                new BlockItem(BONE_FORGE_BLOCK, new FabricItemSettings()));
        Registry.register(Registries.ITEM, getId("bone"), BONE_ITEM);
        Registry.register(BONE_EFFECT_REGISTRY, getId("hog"), new Hog());
        Registry.register(BONE_EFFECT_REGISTRY, getId("swift_stream"), new SwiftStream());

        BoneReloader reloader = new BoneReloader();
        reloader.registerReloadListener();

    }
}
