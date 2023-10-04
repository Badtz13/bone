package me.woach.bone.registries;

import me.woach.bone.Bone;
import me.woach.bone.datapack.AbstractBone;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class BoneRegistry {
    private static final HashMap<Identifier, AbstractBone> idToBone = new HashMap<>();
    private static boolean hasBeenLoaded = false;

    public static void register(Identifier id, AbstractBone bone) {
        if (!hasBeenLoaded) hasBeenLoaded = true;
        if(idToBone.containsKey(id))
            throw new IllegalArgumentException("Duplicate bone id. Tried to register: " + id.toString());

        idToBone.put(id, bone);
        registerBone(id, bone);
    }

    public static boolean hasBeenLoaded() {
        return hasBeenLoaded;
    }

//    public static AbstractBone get(Identifier id) { return idToBone.get(id); }

    public static int size() { return idToBone.size(); }

    private static void registerBone(Identifier boneId, AbstractBone bone) {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, original, source) -> {
            if (source.isBuiltin() && bone.getEntityId().equals(id)) {
                Bone.LOGGER.info("Adding " + boneId + " To loot table for " + id);
                original.pool(LootPool.builder()
                        .conditionally(KilledByPlayerLootCondition.builder())
//                        .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(Items.IRON_SWORD.asItem())))
                        .with(ItemEntry.builder(Bone.BONE_ITEM).build())
                        .build()).build();
            }
        });
    }
}
