package me.woach.bone.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

public class BoneItem extends Item {
    public BoneItem() {
        super(new Item.Settings().maxCount(16).rarity(Rarity.RARE));
    }

    @Override
    public Text getName(ItemStack stack) {
        if (stack.hasNbt() && stack.getNbt().contains("MobSource")) {
            String mobKey = "entity.minecraft." + stack.getNbt().getString("MobSource").toLowerCase();
            String boneKey = "item.minecraft.bone";
            return Text.translatable(mobKey).append(" ").append(Text.translatable(boneKey));
        }
        return super.getName(stack);
    }

}
