package me.woach.bone.datapack;

import me.woach.bone.Bone;
import me.woach.bone.effects.BoneEffect;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

public class AbstractBone {
    private final String name;
    private final BoneEffect effect;
    private final EntityType<?> dropEntity;

    public AbstractBone(String name, BoneEffect effect, EntityType<?> dropEntity) {
        this.name = name;
        this.effect = effect;
        this.dropEntity = dropEntity;
    }

    public Identifier getEntityId() {
        return dropEntity.getLootTableId();
    }
    public void info() {
        Bone.LOGGER.info("Name: " + name);
        Bone.LOGGER.info("Effect: " + effect.getName());
        Bone.LOGGER.info("Mob: " + dropEntity.getName());
    }
}
