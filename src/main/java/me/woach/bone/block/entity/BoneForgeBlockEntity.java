package me.woach.bone.block.entity;

import me.woach.bone.Bone;
import me.woach.bone.block.BoneFireBlock;
import me.woach.bone.bonedata.AbstractBone;
import me.woach.bone.items.EssenceItem;
import me.woach.bone.items.ItemsRegistry;
import me.woach.bone.items.TagsRegistry;
import me.woach.bone.networking.Packets;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Arm;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BoneForgeBlockEntity extends BlockEntity implements Inventory {
    private final DefaultedList<ItemStack> boneAndEquipment = DefaultedList.ofSize(2, ItemStack.EMPTY);
    public static final int BONE_SLOT = 1;
    public static final int EQUIPMENT_SLOT = 0;

    public BoneForgeBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypesRegistry.BONE_FORGE_BLOCK_ENTITY.get(), pos, state);
    }

    private EssenceItem.Types getEssence(World world, BlockPos pos) {
        BlockState belowForge = world.getBlockState(pos.down());
        if (belowForge.getBlock() instanceof BoneFireBlock) {
            return belowForge.get(BoneFireBlock.TYPE);
        }
        return EssenceItem.Types.EMPTY;
    }

    private static short getEquipmentLevel(ItemStack equipment, String enchantId) {
        assert isBoneforgable(equipment);

        NbtCompound nbt = equipment.getNbt();
        if (nbt == null || nbt.get("bone_enchants") == null)
            return 0;
        NbtList enchants = (NbtList) nbt.get("bone_enchants");
        assert enchants != null;

        for (NbtElement enchant : enchants) {
            NbtCompound curr = (NbtCompound) enchant;
            String id = curr.getString("id");
            if (id.equals(enchantId))
                return curr.getShort("lvl");
        }
        return 0;
    }

    public boolean attemptToForge() {
        World world = this.getWorld();
        assert world != null;

        ItemStack bone = getStack(BONE_SLOT);
        if (bone.isEmpty()) return false;
        String boneId = AbstractBone.getBoneEnchantId(bone);
        ItemStack equipment = getStack(EQUIPMENT_SLOT);
        EssenceItem.Types essence = getEssence(world, this.pos);
        if (boneId == null || equipment.isEmpty() || essence == EssenceItem.Types.EMPTY)
            return false;

        short equipmentLevel = getEquipmentLevel(equipment, boneId);
        if (equipmentLevel == 0 && essence == EssenceItem.Types.JORD)
            return forgeLvlZero(bone);
        if (equipmentLevel == 1 && essence == EssenceItem.Types.AEGIR)
            return forge(bone);
        if (equipmentLevel == 2 && essence == EssenceItem.Types.STJARNA)
            return forge(bone);

        return false;
    }

    private boolean forgeLvlZero(ItemStack bone) {
        Bone.LOGGER.info("Correct things for forging have been placed");
        return true;
//        ItemStack equipment = getStack(EQUIPMENT_SLOT);
//        Item convertable = convertToBonesteel(equipment.getItem());
//        if (convertable == null)
//            return false;
//
//
//
//        NbtCompound oldNbt = equipment.getNbt();
//
//        ItemStack newEquipment = new ItemStack(convertable);
//        if (equipment.hasCustomName())
//            newEquipment.setCustomName(equipment.getName());
//
//        return true;
    }

    @Nullable
    private static Item convertToBonesteel(Item equipment) {
        if (equipment instanceof ArmorItem armor) {
            switch (armor.getType()) {
                case HELMET -> { return ItemsRegistry.BONESTEEL_HELMET.get(); }
                case CHESTPLATE -> { return ItemsRegistry.BONESTEEL_CHESTPLATE.get(); }
                case LEGGINGS -> { return ItemsRegistry.BONESTEEL_LEGGINGS.get(); }
                case BOOTS -> { return ItemsRegistry.BONESTEEL_BOOTS.get(); }
            }
        }
        if (equipment instanceof PickaxeItem)
            return ItemsRegistry.BONESTEEL_PICKAXE.get();
        if (equipment instanceof AxeItem)
            return ItemsRegistry.BONESTEEL_AXE.get();
        if (equipment instanceof ShovelItem)
            return ItemsRegistry.BONESTEEL_SHOVEL.get();
        if (equipment instanceof HoeItem)
            return ItemsRegistry.BONESTEEL_HOE.get();
        if (equipment instanceof SwordItem)
            return ItemsRegistry.BONESTEEL_SWORD.get();
        return null;
    }

    private boolean forge(ItemStack bone) {
        Bone.LOGGER.info("Correct things for forging have been placed");
        return true;
    }

    public ItemStack getRenderStack() {
        return boneAndEquipment.get(EQUIPMENT_SLOT);
    }

    public void setInventory(DefaultedList<ItemStack> list) {
        for(int i = 0; i < boneAndEquipment.size(); i++) {
            boneAndEquipment.set(i, list.get(i));
        }
        markDirty();
    }

    public void markDirty() {
        assert world != null;
        attemptToForge();
        if(!world.isClient()) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(boneAndEquipment.size());
            for (ItemStack itemStack : boneAndEquipment) {
                data.writeItemStack(itemStack);
            }
            data.writeBlockPos(getPos());

            for(ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
                ServerPlayNetworking.send(player, Packets.ITEM_SYNC, data);
            }
        }
        super.markDirty();
    }

    public static boolean isBoneforgable(ItemStack stack) {
        return stack.isIn(TagsRegistry.CAN_BONEFORGE);
    }

    public static boolean isBone(ItemStack stack) {
        return stack.isIn(TagsRegistry.CAN_BONE);
    }

    public static boolean canFuelBoneforge(ItemStack stack) {
        return stack.isIn(TagsRegistry.CAN_FUEL_BONEFORGE);
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, boneAndEquipment);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, boneAndEquipment);
    }

    @Override
    public int size() {
        return boneAndEquipment.size();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            ItemStack stack = getStack(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return boneAndEquipment.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = this.getStack(slot);
        boneAndEquipment.set(slot, ItemStack.EMPTY);
        if (!result.isEmpty()) {
            markDirty();
        }
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        markDirty();
        return Inventories.removeStack(boneAndEquipment, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot == 0 && isBoneforgable(stack) ||
                slot == 1 && isBone(stack)) {
            boneAndEquipment.set(slot, stack);
            if (stack.getCount() > stack.getMaxCount()) {
                stack.setCount(stack.getMaxCount());
            }
        }
        markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        boneAndEquipment.clear();
    }
}
