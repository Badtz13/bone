package me.woach.bone.items;

import java.util.List;

import me.woach.bone.Bone;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class DustItemRegistry implements AbstractItemRegistry {

    public static final FabricItemSettings DUSTS = new FabricItemSettings().maxCount(16).rarity(Rarity.RARE);

    public static final Item AMETHYST_DUST = new Item(DUSTS);
    public static final Item LAPIS_DUST = new Item(DUSTS);
    public static final Item EMERALD_DUST = new Item(DUSTS);
    public static final Item DIAMOND_DUST = new Item(DUSTS);

    public static void registerDustItems() {
        Registry.register(Registries.ITEM, Bone.getId("amethyst_dust"), AMETHYST_DUST);
        Registry.register(Registries.ITEM, Bone.getId("lapis_dust"), LAPIS_DUST);
        Registry.register(Registries.ITEM, Bone.getId("emerald_dust"), EMERALD_DUST);
        Registry.register(Registries.ITEM, Bone.getId("diamond_dust"), DIAMOND_DUST);

    }

    @Override
    public List<Item> getItems() {
        return List.of(AMETHYST_DUST, LAPIS_DUST, EMERALD_DUST, DIAMOND_DUST);
    }
}
