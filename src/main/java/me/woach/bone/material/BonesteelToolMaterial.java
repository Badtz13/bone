package me.woach.bone.material;

import me.woach.bone.items.BoneItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class BonesteelToolMaterial implements ToolMaterial {

    public static final BonesteelToolMaterial INSTANCE = new BonesteelToolMaterial();

    @Override
    public int getDurability() {
        return 905;
    }

    @Override
    public float getAttackDamage() {
        return 2.0F;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 7.0F;
    }

    @Override
    public int getMiningLevel() {
        return 3;
    }

    @Override
    public int getEnchantability() {
        return 12;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(BoneItems.BONE_ITEM);
    }
}
