package me.woach.bone.datapack.deserializer;

import com.google.gson.*;
import me.woach.bone.Bone;
import me.woach.bone.datapack.AbstractBone;
import me.woach.bone.effects.BoneEffect;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;

public class BoneDeserializer implements JsonDeserializer<AbstractBone> {

    @Override
    public AbstractBone deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        String name = jsonObject.get("name").getAsString();

        String effectId = jsonObject.get("effect").getAsString();
        BoneEffect effect = Bone.BONE_EFFECT_REGISTRY.get(Identifier.splitOn(effectId, Identifier.NAMESPACE_SEPARATOR));
        if(effect == null)
            throw new JsonParseException("could not find effect " + effectId);

        String entityIdentifier = jsonObject.get("entity").getAsString();
        EntityType<?> entity = Registries.ENTITY_TYPE.get(Identifier.splitOn(entityIdentifier, Identifier.NAMESPACE_SEPARATOR));

        return new AbstractBone(name, effect, entity);
    }
}
