package me.woach.bone.items;

import me.woach.bone.Bone;
import me.woach.bone.block.BoneBlocks;
import me.woach.bone.material.BonesteelArmorMaterial;
import me.woach.bone.material.BonesteelToolMaterial;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.item.ArmorItem.Type;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class BoneItems {
    private static final ArmorMaterial BONESTEEL_ARMOR_MATERIAL = new BonesteelArmorMaterial();
    private static final FabricItemSettings BONESTEEL_SETTINGS = new FabricItemSettings().rarity(Rarity.EPIC);
    public static final FabricItemSettings DUSTS = new FabricItemSettings().maxCount(16).rarity(Rarity.RARE);
    public static final FabricItemSettings ESSENCES = new FabricItemSettings().maxCount(16).rarity(Rarity.EPIC);

    public static final Item BONE_ITEM = register("bone", new BoneItem());
    public static final Item BONE_FORGE_BLOCK_ITEM = register("bone_forge",
            new BlockItem(BoneBlocks.BONE_FORGE_BLOCK, new FabricItemSettings()));
    public static final Item BONESTEEL_HELMET = registerBonesteelArmor("bonesteel_helmet", Type.HELMET);
    public static final Item BONESTEEL_CHESTPLATE = registerBonesteelArmor("bonesteel_chestplate", Type.CHESTPLATE);
    public static final Item BONESTEEL_LEGGINGS = registerBonesteelArmor("bonesteel_leggings", Type.LEGGINGS);
    public static final Item BONESTEEL_BOOTS = registerBonesteelArmor("bonesteel_boots", Type.BOOTS);
    public static final Item BONESTEEL_PICKAXE = register("bonesteel_pickaxe",
            new PickaxeItem(BonesteelToolMaterial.INSTANCE, 1, -2.4F, BONESTEEL_SETTINGS));
    public static final Item BONESTEEL_AXE = register("bonesteel_axe",
            new AxeItem(BonesteelToolMaterial.INSTANCE, 6.0F, -2.9F, BONESTEEL_SETTINGS));
    public static final Item BONESTEEL_SHOVEL = register("bonesteel_shovel",
            new ShovelItem(BonesteelToolMaterial.INSTANCE, 1.5F, -3.0F, BONESTEEL_SETTINGS));
    public static final Item BONESTEEL_HOE = register("bonesteel_hoe",
            new HoeItem(BonesteelToolMaterial.INSTANCE, -1, -1.0F, BONESTEEL_SETTINGS));
    public static final Item BONESTEEL_SWORD = register("bonesteel_sword",
            new SwordItem(BonesteelToolMaterial.INSTANCE, 3, -2.2F, BONESTEEL_SETTINGS));
    public static final Item AMETHYST_DUST = register("amethyst_dust", new Item(DUSTS));
    public static final Item LAPIS_DUST = register("lapis_dust", new Item(DUSTS));
    public static final Item EMERALD_DUST = register("emerald_dust", new Item(DUSTS));
    public static final Item DIAMOND_DUST = register("diamond_dust", new Item(DUSTS));
    public static final Item JORD = register("jord", new Item(ESSENCES));
    public static final Item AEGIR = register("aegir", new Item(ESSENCES));
    public static final Item STJARNA = register("stjarna", new Item(ESSENCES));

    private static Item registerBonesteelArmor(String path, Type type) {
        return register(path, new ArmorItem(BONESTEEL_ARMOR_MATERIAL, type, BONESTEEL_SETTINGS));
    }

    private static Item register(String path, Item item) {
        return Registry.register(Registries.ITEM, Bone.getId(path), item);
    }
}
