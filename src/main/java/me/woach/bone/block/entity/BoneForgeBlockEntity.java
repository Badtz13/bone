package me.woach.bone.block.entity;

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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BoneForgeBlockEntity extends BlockEntity implements Inventory {
    private final DefaultedList<ItemStack> boneAndTool = DefaultedList.ofSize(2, ItemStack.EMPTY);
    public static final int BONE_SLOT = 1;
    public static final int TOOL_SLOT = 0;

    public BoneForgeBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypesRegistry.BONE_FORGE_BLOCK_ENTITY.get(), pos, state);
    }

    public boolean attemptToForge(World world, BlockPos pos) {
        // TODO: Check if there is a bone, tool, and fire essence to forge with, if there is then forge.
        return false;
    }

    public ItemStack getRenderStack() {
        return boneAndTool.get(TOOL_SLOT);
    }

    public void setInventory(DefaultedList<ItemStack> list) {
        for(int i = 0; i < boneAndTool.size(); i++) {
            boneAndTool.set(i, list.get(i));
        }
        markDirty();
    }

    public void markDirty() {
        assert world != null;
        if(!world.isClient()) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(boneAndTool.size());
            for (ItemStack itemStack : boneAndTool) {
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
        Inventories.writeNbt(nbt, boneAndTool);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, boneAndTool);
    }

    @Override
    public int size() {
        return boneAndTool.size();
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
        return boneAndTool.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = this.getStack(slot);
        boneAndTool.set(slot, ItemStack.EMPTY);
        if (!result.isEmpty()) {
            markDirty();
        }
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        markDirty();
        return Inventories.removeStack(boneAndTool, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot == 0 && isBoneforgable(stack) ||
                slot == 1 && isBone(stack)) {
            boneAndTool.set(slot, stack);
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
        boneAndTool.clear();
    }
}
