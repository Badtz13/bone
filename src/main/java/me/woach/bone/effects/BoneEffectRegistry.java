package me.woach.bone.effects;

import me.woach.bone.Bone;
import net.minecraft.registry.Registry;

import java.util.function.Supplier;

public enum BoneEffectRegistry {
    HOG("hog", Hog::new),
    SWIFT_STREAM("swift_stream", SwiftStream::new);

    private final String path;
    private final Supplier<BoneEffect> effectSupplier;
    private BoneEffect effect;

    BoneEffectRegistry(String path, Supplier<BoneEffect> effectSupplier) {
        this.path = path;
        this.effectSupplier = effectSupplier;
    }

    public static void registerAll() {
        for (BoneEffectRegistry effect : values()) {
            Registry.register(Bone.BONE_EFFECT_REGISTRY, Bone.getId(effect.path), effect.get());
        }
    }

    public BoneEffect get() {
        if (effect == null)
            effect = effectSupplier.get();
        return effect;
    }
}
