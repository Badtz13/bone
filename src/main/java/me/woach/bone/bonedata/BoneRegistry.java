package me.woach.bone.bonedata;

import me.woach.bone.bonedata.AbstractBone;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class BoneRegistry {
    private static final HashMap<Identifier, AbstractBone> idToBone = new HashMap<>();

    public static void register(Identifier id, AbstractBone bone) {
        if(idToBone.containsKey(id))
            throw new IllegalArgumentException("Duplicate bone id. Tried to register: " + id.toString());
        idToBone.put(id, bone);
    }

    public static void empty() { idToBone.clear(); }

    public static AbstractBone get(Identifier id) { return idToBone.get(id); }

    public static int size() { return idToBone.size(); }
}
