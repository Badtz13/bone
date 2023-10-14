package me.woach.bone.blocks;

import me.woach.bone.Bone;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class BoneFireBlock extends Block {

    public enum FireType implements StringIdentifiable {
        JORD("jord"),
        AEGIR("aegir"),
        STJARNA("stjarna");

        private final String name;

        FireType(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }

    public static final EnumProperty<FireType> TYPE = EnumProperty.of("type", FireType.class);

    protected static final VoxelShape BASE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    private final float damage;

    public BoneFireBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.LICHEN_GREEN).replaceable().noCollision()
                .breakInstantly()
                .luminance(state -> 15).sounds(BlockSoundGroup.WOOL).pistonBehavior(PistonBehavior.DESTROY)
                .breakInstantly());
        this.damage = 2;
        setDefaultState(getStateManager().getDefaultState().with(TYPE, FireType.JORD));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BASE_SHAPE;
    }

    @Override
    protected void spawnBreakParticles(World world, PlayerEntity player, BlockPos pos, BlockState state) {
    }

    private boolean checkConsumptionParameters(World world, Entity entity, BlockPos pos) {
        BlockEntity be = world.getBlockEntity(pos.up());
        if (be == null || !be.getType().equals(Bone.BONE_FORGE_BLOCK_ENTITY))
            return false;
        BoneForgeBlockEntity forge = (BoneForgeBlockEntity) be;
        if (!entity.getType().equals(EntityType.ITEM))
            return false;
        ItemEntity item = (ItemEntity) entity;
        ItemStack essence = item.getStack();
        return forge.canFuelBoneforge(essence);
    }

    public void convertToFire(World world, BlockPos pos) {
        // TODO: IF SOULSAND IS UNDERNEATH THE BLOCK, MAKE IT SOUL FIRE INSTEAD
        world.setBlockState(pos, Blocks.FIRE.getDefaultState());
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (checkConsumptionParameters(world, entity, pos)) {
            BoneForgeBlockEntity forge = (BoneForgeBlockEntity) world.getBlockEntity(pos.up());
            ItemStack essence = ((ItemEntity) entity).getStack();
            forge.setEssenceLevel(essence);
            entity.discard();
        }
        if (!entity.isFireImmune()) {
            entity.setFireTicks(entity.getFireTicks() + 1);
            if (entity.getFireTicks() == 0) {
                entity.setOnFireFor(8);
            }
        }
        entity.damage(world.getDamageSources().inFire(), this.damage);
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity be = world.getBlockEntity(pos.up());
        if (be != null && be.getType().equals(Bone.BONE_FORGE_BLOCK_ENTITY)) {
            BoneForgeBlockEntity forge = (BoneForgeBlockEntity) be;
            forge.resetEssenceLevel();
        }
        if (!world.isClient()) {
            world.syncWorldEvent(null, WorldEvents.FIRE_EXTINGUISHED, pos, 0);
        }
        super.onBreak(world, pos, state, player);
    }
}
