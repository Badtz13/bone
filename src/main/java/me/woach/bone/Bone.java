package me.woach.bone;

import me.woach.bone.items.ItemsRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
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

    public static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, getId("bone"));



//    private static final ItemGroup.Builder ITEM_GROUP = FabricItemGroup.builder()
//            .icon(() -> new ItemStack(ItemsRegistry.BONE_ITEM))
//            .displayName(Text.translatable("itemGroup.bone.bone"));

    public static final RegistryKey<Registry<BoneEffect>> BONE_EFFECT_REGISTRY_KEY = RegistryKey
                    .ofRegistry(getId("bone_effect_registry"));
    public static final Registry<BoneEffect> BONE_EFFECT_REGISTRY = FabricRegistryBuilder
                    .createSimple(BONE_EFFECT_REGISTRY_KEY).buildAndRegister();
    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
                .displayName(Text.translatable("itemGroup.bone.bone"))
                .icon(() -> new ItemStack(ItemsRegistry.BONE_ITEM.get()))
                .build());

        ItemsRegistry.registerAll();

        Registry.register(BONE_EFFECT_REGISTRY, getId("hog"), new Hog());
        Registry.register(BONE_EFFECT_REGISTRY, getId("swift_stream"), new SwiftStream());

        BoneReloader reloader = new BoneReloader();
        reloader.registerReloadListener();
    }
}