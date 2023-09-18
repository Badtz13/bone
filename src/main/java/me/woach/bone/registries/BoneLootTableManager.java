package me.woach.bone.registries;

import me.woach.bone.Bone;
import me.woach.bone.datapack.AbstractBone;
import me.woach.bone.mixin.LootPoolAccessor;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.entry.LootPoolEntryTypes;
import net.minecraft.util.Identifier;

public class BoneLootTableManager {
    public void addBoneLoot(AbstractBone boneToAdd) {
        Identifier entity = boneToAdd.getEntityId();
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, original, source) -> {
            if (source.isBuiltin() && entity.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(ItemEntry.builder(Bone.BONE_ITEM));

                original.pool(poolBuilder).build();
            }
        });
    }

    public void removeBoneLoot() {
        LootTableEvents.REPLACE.register(((resourceManager, lootManager, id, original, source) -> {
            LootPool[] pools = ((LootPoolAccessor) (original)).getPools();
            for (LootPool pool : pools) {
                for (LootPoolEntry entry: pool.entries) {
                    if(entry.getType() == LootPoolEntryTypes.ITEM) {
                        for (AbstractBone bone: BoneRegistry.getAll()) {
                            Identifier entity = bone.getEntityId();
                            if (source.isBuiltin() && entity.equals(id)) {
                                Bone.LOGGER.info("BOOOOOONNNNEEEE");
                            }
                        }
                    }
                }
            }
            return original;
        }));
    }
}
