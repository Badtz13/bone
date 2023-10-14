package me.woach.bone.items;

import me.woach.bone.Bone;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class TagsRegistry {
    public static final TagKey<Item> CAN_BONE = TagKey.of(RegistryKeys.ITEM, Bone.getId("can_bone"));
    public static final TagKey<Item> CAN_BONEFORGE = TagKey.of(RegistryKeys.ITEM, Bone.getId("can_boneforge"));
    public static final TagKey<Item> CAN_FUEL_BONEFORGE =
            TagKey.of(RegistryKeys.ITEM, Bone.getId("can_fuel_boneforge"));
}
