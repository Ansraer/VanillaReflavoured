package ansraer.reflavoured.client.render;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ExtendedBlockView;

import java.util.Random;

public interface BlockModelAvgBrightnessRenderer {

    boolean tesselateFlatMaxBrightness(ExtendedBlockView extendedBlockView_1, BakedModel bakedModel_1, BlockState blockState_1, BlockPos blockPos_1, BufferBuilder bufferBuilder_1, boolean boolean_1, Random random_1, long long_1);
}
