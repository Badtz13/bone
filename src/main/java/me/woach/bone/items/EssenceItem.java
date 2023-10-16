package me.woach.bone.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.minecraft.util.StringIdentifiable;

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

    public enum Types implements StringIdentifiable {
        EMPTY("empty"),
        JORD("jord"),
        AEGIR("aegir"),
        STJARNA("stjarna");

        private final String name;

        Types(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }
}
