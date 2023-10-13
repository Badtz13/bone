package me.woach.bone.items;

import me.woach.bone.Bone;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class BoneItemGroup {

    private static final ItemGroup.Builder ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(BoneItems.BONE_ITEM))
            .displayName(Text.translatable("itemGroup.bone.bone"));

    public static void registerItemGroup() {
        Registry.register(Registries.ITEM_GROUP, Bone.getId("bone"), ITEM_GROUP.entries((context, entries) -> {
            entries.add(BoneItems.BONE_ITEM);
            entries.add(BoneItems.BONE_FORGE_BLOCK_ITEM);
            entries.add(BoneItems.BONESTEEL_HELMET);
            entries.add(BoneItems.BONESTEEL_CHESTPLATE);
            entries.add(BoneItems.BONESTEEL_LEGGINGS);
            entries.add(BoneItems.BONESTEEL_BOOTS);
            entries.add(BoneItems.BONESTEEL_PICKAXE);
            entries.add(BoneItems.BONESTEEL_AXE);
            entries.add(BoneItems.BONESTEEL_SHOVEL);
            entries.add(BoneItems.BONESTEEL_HOE);
            entries.add(BoneItems.BONESTEEL_SWORD);
            entries.add(BoneItems.AMETHYST_DUST);
            entries.add(BoneItems.LAPIS_DUST);
            entries.add(BoneItems.EMERALD_DUST);
            entries.add(BoneItems.DIAMOND_DUST);
            entries.add(BoneItems.JORD);
            entries.add(BoneItems.AEGIR);
            entries.add(BoneItems.STJARNA);
        }).build());
    }
}
