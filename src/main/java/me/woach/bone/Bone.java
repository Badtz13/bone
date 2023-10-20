package me.woach.bone;

import me.woach.bone.block.BlocksRegistry;
import me.woach.bone.block.entity.BlockEntityTypesRegistry;
import me.woach.bone.effects.BoneEffectRegistry;
import me.woach.bone.items.ItemsRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.woach.bone.bonedata.BoneReloader;
import net.minecraft.registry.RegistryKey;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Bone implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Bone");
    public static Identifier getId(String path) {
            return new Identifier("bone", path);
    }
    public static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, getId("bone"));
    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
                .displayName(Text.translatable("itemGroup.bone.bone"))
                .icon(() -> new ItemStack(ItemsRegistry.BONE_ITEM.get()))
                .build());

        ItemsRegistry.registerAll();
        BlocksRegistry.registerAll();
        BlockEntityTypesRegistry.registerAll();
        BoneEffectRegistry.registerAll();

        BoneReloader.registerReloadListener();
    }
}