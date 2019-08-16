package ansraer.reflavoured.mixin.client;

import ansraer.reflavoured.client.render.BlockModelAvgBrightnessRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ExtendedBlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

/**Since the mixer has rotating parts that are rendered with the block model renderer these parts need to have the same
 * brightness.
 */
@Mixin(BlockModelRenderer.class)
public abstract class BlockModelRendererMixin implements BlockModelAvgBrightnessRenderer {


    @Shadow
    abstract void updateShape(ExtendedBlockView extendedBlockView_1, BlockState blockState_1, BlockPos blockPos_1, int[] ints_1, Direction direction_1, float[] floats_1, BitSet bitSet_1);

    @Override
    public boolean tesselateFlatMaxBrightness(ExtendedBlockView extendedBlockView_1, BakedModel bakedModel_1, BlockState blockState_1, BlockPos blockPos_1, BufferBuilder bufferBuilder_1, boolean boolean_1, Random random_1, long long_1) {
        boolean boolean_2 = false;
        BitSet bitSet_1 = new BitSet(3);
        Direction[] var12 = Direction.values();

        int[] surroundingBrightnesses = {
                blockState_1.getBlockBrightness(extendedBlockView_1, blockPos_1.offset(Direction.UP)),
                blockState_1.getBlockBrightness(extendedBlockView_1, blockPos_1.offset(Direction.NORTH)),
                blockState_1.getBlockBrightness(extendedBlockView_1, blockPos_1.offset(Direction.SOUTH)),
                blockState_1.getBlockBrightness(extendedBlockView_1, blockPos_1.offset(Direction.EAST)),
                blockState_1.getBlockBrightness(extendedBlockView_1, blockPos_1.offset(Direction.WEST)),
        };

        int brightness = (int) Arrays.stream(surroundingBrightnesses).max().orElse(0);


        for(int var14 = 0; var14 < Direction.values().length; ++var14) {
            Direction direction_1 = var12[var14];
            random_1.setSeed(long_1);
            List<BakedQuad> list_1 = bakedModel_1.getQuads(blockState_1, direction_1, random_1);
            if (!list_1.isEmpty() && (!boolean_1 || Block.shouldDrawSide(blockState_1, extendedBlockView_1, blockPos_1, direction_1))) {
                this.tesselateQuadsFlatUnshaded(extendedBlockView_1, blockState_1, blockPos_1, brightness, bufferBuilder_1, list_1, bitSet_1);
                boolean_2 = true;
            }
        }

        random_1.setSeed(long_1);
        List<BakedQuad> list_2 = bakedModel_1.getQuads(blockState_1, (Direction)null, random_1);
        if (!list_2.isEmpty()) {
            System.out.println("drawing some with -1");
            this.tesselateQuadsFlatUnshaded(extendedBlockView_1, blockState_1, blockPos_1, -1, bufferBuilder_1, list_2, bitSet_1);
            boolean_2 = true;
        }

        return boolean_2;
    }

    private void tesselateQuadsFlatUnshaded(ExtendedBlockView extendedBlockView_1, BlockState blockState_1, BlockPos blockPos_1, int int_1, BufferBuilder bufferBuilder_1, List<BakedQuad> list_1, BitSet bitSet_1) {
        Vec3d vec3d_1 = blockState_1.getOffsetPos(extendedBlockView_1, blockPos_1);
        double double_1 = (double)blockPos_1.getX() + vec3d_1.x;
        double double_2 = (double)blockPos_1.getY() + vec3d_1.y;
        double double_3 = (double)blockPos_1.getZ() + vec3d_1.z;
        int int_2 = 0;

        for(int int_3 = list_1.size(); int_2 < int_3; ++int_2) {
            BakedQuad bakedQuad_1 = (BakedQuad)list_1.get(int_2);


            bufferBuilder_1.putVertexData(bakedQuad_1.getVertexData());
            bufferBuilder_1.brightness(int_1, int_1, int_1, int_1);
            /*
            if (bakedQuad_1.hasColor()) {
                int int_4 = this.colorMap.getColorMultiplier(blockState_1, extendedBlockView_1, blockPos_1, bakedQuad_1.getColorIndex());
                float float_1 = (float)(int_4 >> 16 & 255) / 255.0F;
                float float_2 = (float)(int_4 >> 8 & 255) / 255.0F;
                float float_3 = (float)(int_4 & 255) / 255.0F;
                bufferBuilder_1.multiplyColor(float_1, float_2, float_3, 4);
                bufferBuilder_1.multiplyColor(float_1, float_2, float_3, 3);
                bufferBuilder_1.multiplyColor(float_1, float_2, float_3, 2);
                bufferBuilder_1.multiplyColor(float_1, float_2, float_3, 1);
            }
            */

            bufferBuilder_1.postPosition(double_1, double_2, double_3);
        }

    }
}
