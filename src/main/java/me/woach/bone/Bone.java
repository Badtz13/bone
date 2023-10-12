package me.woach.bone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.woach.bone.blocks.BoneForge;
import me.woach.bone.datapack.BoneReloader;
import me.woach.bone.effects.BoneEffect;
import me.woach.bone.effects.Hog;
import me.woach.bone.effects.SwiftStream;
import me.woach.bone.items.BoneItem;
import me.woach.bone.material.BonesteelArmorMaterial;
import me.woach.bone.material.BonesteelToolMaterial;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorItem.Type;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class Bone implements ModInitializer {
        public static final Logger LOGGER = LoggerFactory.getLogger("Bone");

        public static Identifier getId(String path) {
                return new Identifier("bone", path);
        }

        public static final RegistryKey<Registry<BoneEffect>> BONE_EFFECT_REGISTRY_KEY = RegistryKey
                        .ofRegistry(getId("bone_effect_registry"));
        public static final Registry<BoneEffect> BONE_EFFECT_REGISTRY = FabricRegistryBuilder
                        .createSimple(BONE_EFFECT_REGISTRY_KEY).buildAndRegister();

        public static final BoneForge BONE_FORGE_BLOCK = new BoneForge();
        public static final BoneItem BONE_ITEM = new BoneItem();

        public static final FabricItemSettings DUSTS = new FabricItemSettings().maxCount(16).rarity(Rarity.RARE);
        public static final FabricItemSettings ESSENCES = new FabricItemSettings().maxCount(16).rarity(Rarity.EPIC)
                        .fireproof();

        public static final ArmorMaterial BONESTEEL_ARMOR_MATERIAL = new BonesteelArmorMaterial();

        public static final FabricItemSettings BONESTEEL_SETTINGS = new FabricItemSettings().rarity(Rarity.EPIC);

        @Override
        public void onInitialize() {
                Registry.register(Registries.BLOCK, getId("bone_forge"), BONE_FORGE_BLOCK);
                Registry.register(Registries.ITEM, getId("bone_forge"),
                                new BlockItem(BONE_FORGE_BLOCK, new FabricItemSettings()));
                Registry.register(Registries.ITEM, getId("bone"), BONE_ITEM);

                Registry.register(Registries.ITEM, getId("amethyst_dust"), new Item(DUSTS));
                Registry.register(Registries.ITEM, getId("lapis_dust"), new Item(DUSTS));
                Registry.register(Registries.ITEM, getId("emerald_dust"), new Item(DUSTS));
                Registry.register(Registries.ITEM, getId("diamond_dust"), new Item(DUSTS));

                Registry.register(Registries.ITEM, getId("jord"), new Item(ESSENCES));
                Registry.register(Registries.ITEM, getId("aegir"), new Item(ESSENCES));
                Registry.register(Registries.ITEM, getId("stjarna"), new Item(ESSENCES));

                Registry.register(Registries.ITEM, getId("bonesteel_helmet"),
                                new ArmorItem(BONESTEEL_ARMOR_MATERIAL, Type.HELMET, BONESTEEL_SETTINGS));
                Registry.register(Registries.ITEM, getId("bonesteel_chestplate"),
                                new ArmorItem(BONESTEEL_ARMOR_MATERIAL, Type.CHESTPLATE, BONESTEEL_SETTINGS));
                Registry.register(Registries.ITEM, getId("bonesteel_leggings"),
                                new ArmorItem(BONESTEEL_ARMOR_MATERIAL, Type.LEGGINGS, BONESTEEL_SETTINGS));
                Registry.register(Registries.ITEM, getId("bonesteel_boots"),
                                new ArmorItem(BONESTEEL_ARMOR_MATERIAL, Type.BOOTS, BONESTEEL_SETTINGS));

                Registry.register(Registries.ITEM, getId("bonesteel_pickaxe"),
                                new PickaxeItem(BonesteelToolMaterial.INSTANCE, 1, -2.4F, BONESTEEL_SETTINGS));

                Registry.register(Registries.ITEM, getId("bonesteel_axe"),
                                new AxeItem(BonesteelToolMaterial.INSTANCE, 6.0F, -2.9F, BONESTEEL_SETTINGS));

                Registry.register(Registries.ITEM, getId("bonesteel_shovel"),
                                new ShovelItem(BonesteelToolMaterial.INSTANCE, 1.5F, -3.0F, BONESTEEL_SETTINGS));

                Registry.register(Registries.ITEM, getId("bonesteel_hoe"),
                                new HoeItem(BonesteelToolMaterial.INSTANCE, -1, -1.0F, BONESTEEL_SETTINGS));

                Registry.register(Registries.ITEM, getId("bonesteel_sword"),
                                new SwordItem(BonesteelToolMaterial.INSTANCE, 3, -2.2F, BONESTEEL_SETTINGS));

                Registry.register(BONE_EFFECT_REGISTRY, getId("hog"), new Hog());
                Registry.register(BONE_EFFECT_REGISTRY, getId("swift_stream"), new SwiftStream());

                BoneReloader reloader = new BoneReloader();
                reloader.registerReloadListener();
        }
}