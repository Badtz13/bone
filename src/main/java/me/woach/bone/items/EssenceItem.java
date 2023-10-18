package me.woach.bone.items;

import java.util.List;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Rarity;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.world.World;

public class EssenceItem extends Item {
    public EssenceItem() {
        super(new FabricItemSettings().maxCount(16).rarity(Rarity.EPIC));
    }

    public static Types itemStackToTypes(ItemStack essence) {
        if (essence.isOf(ItemsRegistry.JORD.get()))
            return Types.JORD;
        if (essence.isOf(ItemsRegistry.AEGIR.get()))
            return Types.AEGIR;
        if (essence.isOf(ItemsRegistry.STJARNA.get()))
            return Types.STJARNA;
        return Types.EMPTY;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        Types essenceType = itemStackToTypes(stack);
        if (essenceType != Types.EMPTY) {
            tooltip.add(Text.translatable("item.bone.tier", essenceType.getTier())
                    .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xAAAAAA)).withItalic(true)));
        }
    }

    public enum Types implements StringIdentifiable {
        EMPTY("empty", 0),
        JORD("jord", 1),
        AEGIR("aegir", 2),
        STJARNA("stjarna", 3);

        private final String name;
        private final int tier;

        Types(String name, int tier) {
            this.name = name;
            this.tier = tier;
        }

        public int getTier() {
            return this.tier;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }

}
