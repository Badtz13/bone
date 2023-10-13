package me.woach.bone.material;

import me.woach.bone.items.BoneItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class BonesteelArmorMaterial implements ArmorMaterial {
    private static final int[] BASE_DURABILITY = { 265, 385, 360, 312 };
    private static final int[] PROTECTION_VALUES = { 2, 5, 6, 2 };

    @Override
    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY[type.getEquipmentSlot().getEntitySlotId()] * 15;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return PROTECTION_VALUES[type.getEquipmentSlot().getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(BoneItems.BONE_ITEM);
    }

    @Override
    public String getName() {
        return "bonesteel";
    }

    @Override
    public float getToughness() {
        return 1.2F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
