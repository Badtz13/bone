package me.woach.bone.effects;

import me.woach.bone.Bone;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.LuckEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Supplier;

public enum BoneEffectRegistry {
    GREED("greed", GreedEffect::new);

    private final String path;
    private final Supplier<Enchantment> effectSupplier;
    private Enchantment effect;

    BoneEffectRegistry(String path, Supplier<Enchantment> effectSupplier) {
        this.path = path;
        this.effectSupplier = effectSupplier;
    }

    public static void registerAll() {
        for (BoneEffectRegistry effect : values()) {
            Registry.register(Registries.ENCHANTMENT, Bone.getId(effect.path), effect.get());
        }
    }

    public Enchantment get() {
        if (effect == null)
            effect = effectSupplier.get();
        return effect;
    }
}
