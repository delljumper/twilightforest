package twilightforest.structures.courtyard;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import twilightforest.block.BlockTFNagastoneStairs;
import twilightforest.block.TFBlocks;
import twilightforest.structures.RandomizedTemplateProcessor;

import javax.annotation.Nullable;

public class CourtyardTerraceTemplateProcessor extends RandomizedTemplateProcessor {

    public CourtyardTerraceTemplateProcessor(BlockPos pos, PlacementSettings settings) {
        super(pos, settings);
    }

    @Nullable
    @Override
    public Template.BlockInfo processBlock(World worldIn, BlockPos pos, Template.BlockInfo blockInfo) {
        if (shouldPlaceBlock()) {
            IBlockState state = blockInfo.blockState;

            final IBlockState SMOOTHBRICK_SLAB_STATE = Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SMOOTHBRICK);
            final IBlockState SMOOTHBRICK_STATE = Blocks.STONEBRICK.getDefaultState();

            final IBlockState GRASS = Blocks.GRASS.getDefaultState();
            //final IBlockState SEAMLESS_STONE_DOUBLESLAB = Blocks.DOUBLE_STONE_SLAB.getDefaultState().withProperty(BlockDoubleStoneSlab.VARIANT, BlockStoneSlab.EnumType.STONE).withProperty(BlockDoubleStoneSlab.SEAMLESS, true);

            if (state == SMOOTHBRICK_STATE) {
                IBlockState stateCheck = worldIn.getBlockState(pos);
                if (stateCheck == SMOOTHBRICK_SLAB_STATE)
                    state = stateCheck;
                else if (stateCheck.getMaterial() == Material.AIR)
                    return null;
            }

            if (state == SMOOTHBRICK_SLAB_STATE) {
                IBlockState stateCheck = worldIn.getBlockState(pos);
                if (stateCheck.getMaterial() == Material.AIR)
                    return null;
            }

            if (state == GRASS) {
                IBlockState stateCheck = worldIn.getBlockState(pos);
                if (stateCheck.getBlock() == TFBlocks.etched_nagastone)
                    state = stateCheck;
            }

            Block block = state.getBlock();

            if (block == Blocks.STONEBRICK && state != Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED))
                return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, state.withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.values()[random.nextInt(3)]), null);

            if (state == Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.STONE))
                return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, state.withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.COBBLESTONE), null);

            if (block == TFBlocks.etched_nagastone)
                return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, translateState(state, randomBlock(TFBlocks.etched_nagastone_mossy, TFBlocks.etched_nagastone_weathered), BlockDirectional.FACING), null);

            if (block == TFBlocks.nagastone_pillar)
                return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, translateState(state, randomBlock(TFBlocks.nagastone_pillar_mossy, TFBlocks.nagastone_pillar_weathered), BlockRotatedPillar.AXIS), null);

            if (block == TFBlocks.nagastone_stairs)
                return random.nextBoolean() ? blockInfo : new Template.BlockInfo(pos, translateState(state, randomBlock(TFBlocks.nagastone_stairs, TFBlocks.nagastone_stairs), BlockTFNagastoneStairs.DIRECTION, BlockTFNagastoneStairs.FACING, BlockTFNagastoneStairs.HALF, BlockTFNagastoneStairs.SHAPE), null);

            return blockInfo;
        }

        return null;
    }
}