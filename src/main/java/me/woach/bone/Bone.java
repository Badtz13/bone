package me.woach.bone;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.woach.bone.blocks.BoneFireBlock;
import me.woach.bone.blocks.BoneForgeBlock;
import me.woach.bone.blocks.BoneForgeBlockEntity;
import me.woach.bone.datapack.BoneReloader;
import me.woach.bone.effects.BoneEffect;
import me.woach.bone.effects.Hog;
import me.woach.bone.effects.SwiftStream;
import me.woach.bone.items.AbstractItemRegistry;
import me.woach.bone.items.BoneItem;
import me.woach.bone.items.BonesteelItemRegistry;
import me.woach.bone.items.DustItemRegistry;
import me.woach.bone.items.EssenceItemRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Bone implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Bone");

    public static Identifier getId(String path) {
        return new Identifier("bone", path);
    }

    public static final List<AbstractItemRegistry> ITEM_REGISTRIES = List.of(new DustItemRegistry(),
            new EssenceItemRegistry(), new BonesteelItemRegistry());

    public static final RegistryKey<Registry<BoneEffect>> BONE_EFFECT_REGISTRY_KEY = RegistryKey
            .ofRegistry(getId("bone_effect_registry"));
    public static final Registry<BoneEffect> BONE_EFFECT_REGISTRY = FabricRegistryBuilder
            .createSimple(BONE_EFFECT_REGISTRY_KEY).buildAndRegister();
    public static final BoneForgeBlock BONE_FORGE_BLOCK = new BoneForgeBlock();

    public static final BoneFireBlock BONE_FIRE_BLOCK = new BoneFireBlock();

    public static final BlockEntityType<BoneForgeBlockEntity> BONE_FORGE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            getId("bone_forge_entity"),
            FabricBlockEntityTypeBuilder.create(BoneForgeBlockEntity::new, BONE_FORGE_BLOCK).build());
    public static final BoneItem BONE_ITEM = new BoneItem();
    private static final ItemGroup.Builder ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(BONE_ITEM))
            .displayName(Text.translatable("itemGroup.bone.bone"));

    public static final TagKey<Item> CAN_BONE = TagKey.of(RegistryKeys.ITEM, getId("can_bone"));
    public static final TagKey<Item> CAN_BONEFORGE = TagKey.of(RegistryKeys.ITEM, getId("can_boneforge"));
    public static final TagKey<Item> CAN_FUEL_BONEFORGE = TagKey.of(RegistryKeys.ITEM, getId("can_fuel_boneforge"));

    @Override
    public void onInitialize() {

        Registry.register(Registries.ITEM_GROUP, getId("bone"), ITEM_GROUP.entries((context, entries) -> {
            entries.add(BONE_ITEM);
            entries.add(BONE_FORGE_BLOCK);
            for (AbstractItemRegistry ItemRegistry : ITEM_REGISTRIES) {
                for (Item item : ItemRegistry.getItems()) {
                    entries.add(item);
                }
            }
        }).build());

        Registry.register(Registries.BLOCK, getId("bone_forge"), BONE_FORGE_BLOCK);
        Registry.register(Registries.ITEM, getId("bone_forge"),
                new BlockItem(BONE_FORGE_BLOCK, new FabricItemSettings()));
        Registry.register(Registries.BLOCK, getId("bone_fire"), BONE_FIRE_BLOCK);
        Registry.register(Registries.ITEM, getId("bone_fire"),
                new BlockItem(BONE_FIRE_BLOCK, new FabricItemSettings()));
        Registry.register(Registries.ITEM, getId("bone"), BONE_ITEM);

        EssenceItemRegistry.registerEssenceItems();
        DustItemRegistry.registerDustItems();
        BonesteelItemRegistry.registerBonesteelItems();

        Registry.register(BONE_EFFECT_REGISTRY, getId("hog"), new Hog());
        Registry.register(BONE_EFFECT_REGISTRY, getId("swift_stream"), new SwiftStream());

        BoneReloader reloader = new BoneReloader();
        reloader.registerReloadListener();
    }
}