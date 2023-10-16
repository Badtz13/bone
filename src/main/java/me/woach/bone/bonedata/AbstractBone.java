package me.woach.bone.bonedata;

import com.google.gson.*;
import me.woach.bone.Bone;
import me.woach.bone.effects.BoneEffect;
import me.woach.bone.items.ItemsRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.lang.reflect.Type;
import java.util.function.Consumer;

public class AbstractBone {
    private final boolean enabled;
    private final int chance;
    private final BoneEffect effect;
    private final Identifier dropEntity;
    private final Random rng = Random.create();

    public AbstractBone(boolean enabled, int chance,
                        BoneEffect effect, Identifier dropEntity) {
        this.enabled = enabled;
        this.chance = chance;
        this.effect = effect;
        this.dropEntity = dropEntity;
    }

    public void tryDrop(short lootingLvl, Consumer<ItemStack> dropper) {
        if (!this.isEnabled())
            return;

        if (!rollChance(lootingLvl))
            return;

        ItemStack boneToDrop = new ItemStack(ItemsRegistry.BONE_ITEM.get());

        NbtCompound nbtData = new NbtCompound();
        nbtData.putString("MobSource", dropEntity.toString());

        boneToDrop.setNbt(nbtData);

        dropper.accept(boneToDrop);
    }

    private boolean rollChance(short lootingLvl) {
        for (int i = 0; i < lootingLvl; i++) {
            if(rollChance())
                return true;
        }
        return false;
    }

    private boolean rollChance() {
        int roll = rng.nextInt(1000);

        Bone.LOGGER.info("Roll was: " + roll + ", Chance was: " + this.chance);

        return roll <= this.chance;
    }

    public Identifier getEntityId() {
        return dropEntity;
    }

    private boolean isEnabled() { return enabled; }

    public static class Deserializer implements JsonDeserializer<AbstractBone> {

        @Override
        public AbstractBone deserialize(JsonElement json, Type type, JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject jsonObject = json.getAsJsonObject();

            JsonElement checkEnabled = jsonObject.get("enabled");
            boolean enabled;
            if (checkEnabled == null)
                enabled = true;
            else
                enabled = checkEnabled.getAsBoolean();

            JsonElement checkChance = jsonObject.get("chance");
            int chance;
            if (checkChance == null)
                chance = 125;
            else
                chance = checkChance.getAsInt();

            String effectId = jsonObject.get("effect").getAsString();
            BoneEffect effect = Bone.BONE_EFFECT_REGISTRY.get(Identifier.splitOn(effectId, Identifier.NAMESPACE_SEPARATOR));
            if(effect == null)
                throw new JsonParseException("could not find effect " + effectId);

            String entityIdentifier = jsonObject.get("entity").getAsString();
            Identifier entity = Identifier.splitOn(entityIdentifier, Identifier.NAMESPACE_SEPARATOR);

            return new AbstractBone(enabled, chance, effect, entity);
        }
    }
}
