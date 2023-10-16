package me.woach.bone.block;

import me.woach.bone.Bone;
import me.woach.bone.block.entity.BlockEntityTypesRegistry;
import me.woach.bone.block.entity.BoneForgeBlockEntity;
import me.woach.bone.items.EssenceItem;
import me.woach.bone.items.ItemsRegistry;
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
import net.minecraft.world.WorldView;

@SuppressWarnings("deprecation")
public class BoneFireBlock extends Block {
    public static final EnumProperty<EssenceItem.Types> TYPE = EnumProperty.of("type", EssenceItem.Types.class);
    private final float damage;

    public BoneFireBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).replaceable().noCollision().breakInstantly()
                .luminance(state -> 15).sounds(BlockSoundGroup.WOOL).pistonBehavior(PistonBehavior.DESTROY).nonOpaque());
        this.damage = 2;
        setDefaultState(getStateManager().getDefaultState().with(TYPE, EssenceItem.Types.EMPTY));
    }

    public BlockState getEssenceState(ItemStack essence) {
        EssenceItem.Types type = EssenceItem.itemStackToTypes(essence);
        return getStateManager().getDefaultState().with(TYPE, type);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    }
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSolidBlock(world,pos);
    }

    public static void convertToFire(World world, BlockPos pos) {
        // TODO: IF SOULSAND IS UNDERNEATH THE BLOCK, MAKE IT SOUL FIRE INSTEAD
        world.setBlockState(pos, Blocks.FIRE.getDefaultState());
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity.getType().equals(EntityType.ITEM)) {
            ItemEntity item = (ItemEntity) entity;
            ItemStack essence = item.getStack();
            if (BoneForgeBlockEntity.canFuelBoneforge(essence)) {
                world.setBlockState(pos, getEssenceState(essence));
                entity.discard();
            }
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
        // POTENTIALLY NEED TO ADD LOGIC HERE IF BONEFORGE USES AN ANIMATION
        if (!world.isClient()) {
            world.syncWorldEvent(null, WorldEvents.FIRE_EXTINGUISHED, pos, 0);
        }
        super.onBreak(world, pos, state, player);
    }

}
