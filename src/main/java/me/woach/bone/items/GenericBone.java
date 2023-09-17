package me.woach.bone.items;

import me.woach.bone.effects.BoneEffect;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class GenericBone extends Item {

    private final String name;

    private final BoneEffect effect;

    private final EntityType<?> dropEntity;

    public GenericBone(String name, BoneEffect effect, EntityType<?> dropEntity) {
        super(new Item.Settings());
        this.name = name;
        this.effect = effect;
        // TODO: Ensure entity exists? (May not need this)
        this.dropEntity = dropEntity;
    }
}