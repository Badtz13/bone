package me.woach.bone.datapack.deserializer;

import com.google.gson.*;
import me.woach.bone.Bone;
import me.woach.bone.effects.BoneEffect;
import me.woach.bone.items.GenericBone;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Type;

public class BoneDeserializer implements JsonDeserializer<GenericBone> {

    @Override
    public GenericBone deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        String name = jsonObject.get("name").getAsString();

        String effectId = jsonObject.get("effect").getAsString();
        BoneEffect effect = Bone.BONE_EFFECT_REGISTRY.get(Identifier.splitOn(effectId, Identifier.NAMESPACE_SEPARATOR));

        String entityIdentifier = jsonObject.get("entity").getAsString();
        EntityType<?> entity = Registries.ENTITY_TYPE.get(Identifier.splitOn(entityIdentifier, Identifier.NAMESPACE_SEPARATOR));

        return new GenericBone(name, effect, entity);
    }
}
