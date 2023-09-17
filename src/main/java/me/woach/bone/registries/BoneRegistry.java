package me.woach.bone.registries;

import me.woach.bone.items.GenericBone;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class BoneRegistry {
    private static final HashMap<Identifier, GenericBone> idToBone = new HashMap<>();

    public static GenericBone register(Identifier id, GenericBone bone) {
        if(idToBone.containsKey(id))
            throw new IllegalArgumentException("Duplicate bone id. Tried to register: " + id.toString());

        idToBone.put(id, bone);
        return bone;
    }

    public static GenericBone get(Identifier id) { return idToBone.get(id); }

    public static void empty() { idToBone.clear(); }

    public static int size() { return idToBone.size(); }
}
