package me.woach.bone.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;

public class BoneFireBlock extends AbstractFireBlock {
    public BoneFireBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).replaceable().noCollision().breakInstantly()
                .luminance(state -> 15).sounds(BlockSoundGroup.WOOL).pistonBehavior(PistonBehavior.DESTROY), 2.0f);
    }

    @Override
    protected boolean isFlammable(BlockState state) {
        return true;
    }
}
