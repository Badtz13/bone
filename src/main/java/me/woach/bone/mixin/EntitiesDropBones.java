package me.woach.bone.mixin;

import me.woach.bone.bonedata.AbstractBone;
import me.woach.bone.bonedata.BoneRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class EntitiesDropBones extends Entity {
    @Shadow @Nullable protected PlayerEntity attackingPlayer;

    @Shadow protected int playerHitTimer;

    public EntitiesDropBones(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "drop", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;dropLoot(Lnet/minecraft/entity/damage/DamageSource;Z)V"))
    private void dropBone(DamageSource source, CallbackInfo ci) {
        // Only trigger when killed by player
        if (this.attackingPlayer == null || this.playerHitTimer <= 0)
            return;

        short lootingLvl = 0;

        // Check for enchantment levels
        NbtCompound nbt = this.attackingPlayer.getMainHandStack().getNbt();
        if (nbt != null && nbt.get("Enchantments") != null) {
            NbtList enchants = (NbtList) nbt.get("Enchantments");
            assert enchants != null;
            for (NbtElement enchant : enchants) {
                NbtCompound curr = (NbtCompound) enchant;
                String id = curr.getString("id");
                if (id.equals("minecraft:looting"))
                    lootingLvl = curr.getShort("lvl");
            }
        }

        Identifier entityId = EntityType.getId(this.getType());

        // Check if entity is in BoneRegistry
        AbstractBone bone = BoneRegistry.get(entityId);
        if(bone == null)
            return;

        bone.tryDrop(lootingLvl, this::dropStack);
    }
}
