package me.woach.bone.items;

import java.util.Arrays;
import java.util.List;

import me.woach.bone.Bone;
import me.woach.bone.material.BonesteelArmorMaterial;
import me.woach.bone.material.BonesteelToolMaterial;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorItem.Type;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class BonesteelItemRegistry implements AbstractItemRegistry {

        public static final ArmorMaterial BONESTEEL_ARMOR_MATERIAL = new BonesteelArmorMaterial();
        public static final FabricItemSettings BONESTEEL_SETTINGS = new FabricItemSettings().rarity(Rarity.EPIC);

        public static final ArmorItem BONESTEEL_HELMET = new ArmorItem(BONESTEEL_ARMOR_MATERIAL, Type.HELMET,
                        BONESTEEL_SETTINGS);
        public static final ArmorItem BONESTEEL_CHESTPLATE = new ArmorItem(BONESTEEL_ARMOR_MATERIAL, Type.CHESTPLATE,
                        BONESTEEL_SETTINGS);
        public static final ArmorItem BONESTEEL_LEGGINGS = new ArmorItem(BONESTEEL_ARMOR_MATERIAL, Type.LEGGINGS,
                        BONESTEEL_SETTINGS);
        public static final ArmorItem BONESTEEL_BOOTS = new ArmorItem(BONESTEEL_ARMOR_MATERIAL, Type.BOOTS,
                        BONESTEEL_SETTINGS);
        public static final PickaxeItem BONESTEEL_PICKAXE = new PickaxeItem(BonesteelToolMaterial.INSTANCE, 1, -2.4F,
                        BONESTEEL_SETTINGS);
        public static final AxeItem BONESTEEL_AXE = new AxeItem(BonesteelToolMaterial.INSTANCE, 6.0F, -2.9F,
                        BONESTEEL_SETTINGS);
        public static final ShovelItem BONESTEEL_SHOVEL = new ShovelItem(BonesteelToolMaterial.INSTANCE, 1.5F, -3.0F,
                        BONESTEEL_SETTINGS);
        public static final HoeItem BONESTEEL_HOE = new HoeItem(BonesteelToolMaterial.INSTANCE, -1, -1.0F,
                        BONESTEEL_SETTINGS);
        public static final SwordItem BONESTEEL_SWORD = new SwordItem(BonesteelToolMaterial.INSTANCE, 3, -2.2F,
                        BONESTEEL_SETTINGS);

        public static void registerBonesteelItems() {
                Registry.register(Registries.ITEM, Bone.getId("bonesteel_helmet"), BONESTEEL_HELMET);
                Registry.register(Registries.ITEM, Bone.getId("bonesteel_chestplate"), BONESTEEL_CHESTPLATE);
                Registry.register(Registries.ITEM, Bone.getId("bonesteel_leggings"), BONESTEEL_LEGGINGS);
                Registry.register(Registries.ITEM, Bone.getId("bonesteel_boots"), BONESTEEL_BOOTS);
                Registry.register(Registries.ITEM, Bone.getId("bonesteel_pickaxe"), BONESTEEL_PICKAXE);
                Registry.register(Registries.ITEM, Bone.getId("bonesteel_axe"), BONESTEEL_AXE);
                Registry.register(Registries.ITEM, Bone.getId("bonesteel_shovel"), BONESTEEL_SHOVEL);
                Registry.register(Registries.ITEM, Bone.getId("bonesteel_hoe"), BONESTEEL_HOE);
                Registry.register(Registries.ITEM, Bone.getId("bonesteel_sword"), BONESTEEL_SWORD);
        }

        @Override
        public List<Item> getItems() {
                return Arrays.asList(
                                BONESTEEL_HELMET,
                                BONESTEEL_CHESTPLATE,
                                BONESTEEL_LEGGINGS,
                                BONESTEEL_BOOTS,
                                BONESTEEL_PICKAXE,
                                BONESTEEL_AXE,
                                BONESTEEL_SHOVEL,
                                BONESTEEL_HOE,
                                BONESTEEL_SWORD);
        }
}
