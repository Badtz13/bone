package me.woach.bone.registries;

import me.woach.bone.datapack.AbstractBone;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class BoneRegistry {
    private static final HashMap<Identifier, AbstractBone> idToBone = new HashMap<>();

    public static AbstractBone register(Identifier id, AbstractBone bone) {
        if(idToBone.containsKey(id))
            throw new IllegalArgumentException("Duplicate bone id. Tried to register: " + id.toString());

        idToBone.put(id, bone);
        return bone;
    }

    public static AbstractBone get(Identifier id) { return idToBone.get(id); }

    public static void empty() { idToBone.clear(); }

    public static int size() { return idToBone.size(); }
}
