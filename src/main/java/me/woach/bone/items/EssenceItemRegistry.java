package me.woach.bone.items;

import java.util.List;

import me.woach.bone.Bone;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class EssenceItemRegistry implements AbstractItemRegistry {

    public static final FabricItemSettings ESSENCES = new FabricItemSettings().maxCount(16).rarity(Rarity.EPIC);

    public static final Item JORD = new Item(ESSENCES);
    public static final Item AEGIR = new Item(ESSENCES);
    public static final Item STJARNA = new Item(ESSENCES);

    public static void registerEssenceItems() {
        Registry.register(Registries.ITEM, Bone.getId("jord"), JORD);
        Registry.register(Registries.ITEM, Bone.getId("aegir"), AEGIR);
        Registry.register(Registries.ITEM, Bone.getId("stjarna"), STJARNA);
    }

    @Override
    public List<Item> getItems() {
        return List.of(JORD, AEGIR, STJARNA);
    }
}
