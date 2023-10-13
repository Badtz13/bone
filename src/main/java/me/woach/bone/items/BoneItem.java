package me.woach.bone.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

public class BoneItem extends Item {
    public BoneItem() {
        super(new Item.Settings().maxCount(16).rarity(Rarity.UNCOMMON));
    }

    @Override
    public Text getName(ItemStack stack) {
        if (stack.hasNbt()) {
            assert stack.getNbt() != null;
            if (stack.getNbt().contains("MobSource")) {
                String mobKey = "entity." + stack.getNbt().getString("MobSource").toLowerCase().replace(":", ".");
                String boneKey = "item.minecraft.bone";
                return Text.translatable(mobKey).append(" ").append(Text.translatable(boneKey));
            }
        }
        return super.getName(stack);
    }

}
