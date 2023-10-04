package me.woach.bone.datapack;

import com.google.gson.*;
import me.woach.bone.Bone;
import me.woach.bone.effects.BoneEffect;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;

public class AbstractBone {
    private final boolean enabled;
    private final float chance;
    private final BoneEffect effect;
    private final EntityType<?> dropEntity;

    public AbstractBone(boolean enabled, float chance,
                        BoneEffect effect, EntityType<?> dropEntity) {
        this.enabled = enabled;
        this.chance = chance;
        this.effect = effect;
        this.dropEntity = dropEntity;
    }

    public Identifier getEntityId() {
        return dropEntity.getLootTableId();
    }
    public void info() {
        Bone.LOGGER.info("Effect: " + effect.getName());
        Bone.LOGGER.info("Mob: " + dropEntity.getName());
    }
    public static class Deserializer implements JsonDeserializer<AbstractBone> {

        @Override
        public AbstractBone deserialize(JsonElement json, Type type, JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject jsonObject = json.getAsJsonObject();

            boolean enabled = jsonObject.get("enabled").getAsBoolean();

            float chance = jsonObject.get("chance").getAsFloat();

            String effectId = jsonObject.get("effect").getAsString();
            BoneEffect effect = Bone.BONE_EFFECT_REGISTRY.get(Identifier.splitOn(effectId, Identifier.NAMESPACE_SEPARATOR));
            if(effect == null)
                throw new JsonParseException("could not find effect " + effectId);

            String entityIdentifier = jsonObject.get("entity").getAsString();
            EntityType<?> entity = Registries.ENTITY_TYPE.get(Identifier.splitOn(entityIdentifier,
                    Identifier.NAMESPACE_SEPARATOR));

            return new AbstractBone(enabled, chance, effect, entity);
        }
    }
}
