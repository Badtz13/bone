package me.woach.bone.mixin;

import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootTable.class)
public interface LootPoolAccessor {
    @Accessor
    LootPool[] getPools();
    @Accessor("pools")
    @Mutable
    void setPools(LootPool[] pools);
}
