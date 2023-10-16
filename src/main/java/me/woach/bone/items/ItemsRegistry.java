package me.woach.bone.items;

import java.util.Arrays;
import java.util.function.Supplier;

import me.woach.bone.Bone;
import me.woach.bone.block.BlocksRegistry;
import me.woach.bone.material.BonesteelArmorMaterial;
import me.woach.bone.material.BonesteelToolMaterial;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.data.client.BlockStateVariantMap.QuadFunction;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorItem.Type;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public enum ItemsRegistry {
    BONE_ITEM("bone", BoneItem::new),
    BONE_FORGE_BLOCK_ITEM("bone_forge", () -> new BlockItem(BlocksRegistry.BONE_FORGE_BLOCK.get(),
            new FabricItemSettings())),
    FIREWOOD_BLOCK_BLOCK_ITEM("firewood_block", () -> new BlockItem(BlocksRegistry.FIREWOOD_BLOCK.get(),
            new FabricItemSettings())),
    BONESTEEL_HELMET("bonesteel_helmet", () -> ItemBuilder.newItemArmor(Type.HELMET)),
    BONESTEEL_CHESTPLATE("bonesteel_chestplate", () -> ItemBuilder.newItemArmor(Type.CHESTPLATE)),
    BONESTEEL_LEGGINGS("bonesteel_leggings", () -> ItemBuilder.newItemArmor(Type.LEGGINGS)),
    BONESTEEL_BOOTS("bonesteel_boots", () -> ItemBuilder.newItemArmor(Type.BOOTS)),
    BONESTEEL_PICKAXE("bonesteel_pickaxe", () -> ItemBuilder.newToolItem(PickaxeItem::new, 1, -2.4f)),
    BONESTEEL_AXE("bonesteel_axe", () -> ItemBuilder.newToolItem(AxeItem::new, 6.0f, -2.9f)),
    BONESTEEL_SHOVEL("bonesteel_shovel", () -> ItemBuilder.newToolItem(ShovelItem::new, 1.5F, -3.0F)),
    BONESTEEL_HOE("bonesteel_hoe", () -> ItemBuilder.newToolItem(HoeItem::new, -1, -1.0F)),
    BONESTEEL_SWORD("bonesteel_sword", () -> ItemBuilder.newToolItem(HoeItem::new, 3, -2.2F)),
    AMETHYST_DUST("amethyst_dust", ItemBuilder::newItemDust),
    LAPIS_DUST("lapis_dust", ItemBuilder::newItemDust),
    EMERALD_DUST("emerald_dust", ItemBuilder::newItemDust),
    DIAMOND_DUST("diamond_dust", ItemBuilder::newItemDust),
    JORD("jord", EssenceItem::new),
    AEGIR("aegir", EssenceItem::new),
    STJARNA("stjarna", EssenceItem::new);

    private final String path;
    private final Supplier<Item> itemSupplier;
    private final boolean addToItemGroup;
    private Item item;

    ItemsRegistry(String path, Supplier<Item> itemSupplier) {
        this(path, itemSupplier, true);
    }

    ItemsRegistry(String path, Supplier<Item> itemSupplier, boolean addToItemGroup) {
        this.path = path;
        this.itemSupplier = itemSupplier;
        this.addToItemGroup = addToItemGroup;
    }

    public static void registerAll() {
        for (ItemsRegistry item : values()) {
            register(item.path, item.get());
        }
        ItemGroupEvents.modifyEntriesEvent(Bone.ITEM_GROUP).register(entries -> entries.addAll(
                Arrays.stream(values()).filter(item -> item.addToItemGroup).map(item -> item.get().getDefaultStack())
                        .toList()));
    }

    public Item get() {
        if (item == null)
            item = itemSupplier.get();
        return item;
    }

    private static void register(String path, Item item) {
        Registry.register(Registries.ITEM, Bone.getId(path), item);
    }

    private static class ItemBuilder {
        protected static Item newItemDust() {
            return new Item(new FabricItemSettings().maxCount(16).rarity(Rarity.EPIC));
        }

        protected static Item newItemArmor(Type armorType) {
            return new ArmorItem(new BonesteelArmorMaterial(), armorType, new FabricItemSettings().rarity(Rarity.EPIC));
        }

        protected static Item newToolItem(QuadFunction<ToolMaterial, Integer, Float, Item.Settings, Item> toolCreator,
                int damage, float attackspeed) {
            return toolCreator.apply(BonesteelToolMaterial.INSTANCE, damage, attackspeed,
                    new FabricItemSettings().rarity(Rarity.EPIC));
        }

        protected static Item newToolItem(QuadFunction<ToolMaterial, Float, Float, Item.Settings, Item> toolCreator,
                float damage, float attackspeed) {
            return toolCreator.apply(BonesteelToolMaterial.INSTANCE, damage, attackspeed,
                    new FabricItemSettings().rarity(Rarity.EPIC));
        }

    }
}
