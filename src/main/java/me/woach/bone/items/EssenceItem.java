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

    public static ItemStack typeToItemStack(Types essence) {
        switch (essence) {
            case JORD -> {
                return ItemsRegistry.JORD.get().getDefaultStack();
            }
            case AEGIR -> {
                return ItemsRegistry.AEGIR.get().getDefaultStack();
            }
            case STJARNA -> {
                return ItemsRegistry.STJARNA.get().getDefaultStack();
            }
        }
        return ItemStack.EMPTY;
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

        public static Types fromString(String name) {
            if (name.equals("jord"))
                return Types.JORD;
            if (name.equals("aegir"))
                return Types.AEGIR;
            if (name.equals("stjarna"))
                return Types.STJARNA;
            return Types.EMPTY;
        }
        @Override
        public String asString() {
            return this.name;
        }
    }
}
