package ansraer.reflavoured.client.render.block.concrete_mixer;

import ansraer.reflavoured.VanillaReflavouredClient;
import ansraer.reflavoured.client.render.BlockModelAvgBrightnessRenderer;
import ansraer.reflavoured.common.block.entity.ConcreteMixerBlockEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.model.ChestEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.SpriteAtlasTexture;

import java.util.Random;

public class ConcreteMixerBlockEntityRender extends BlockEntityRenderer<ConcreteMixerBlockEntity> {
/*
    public ConcreteMixerBlockEntityRender(){
        ModelLoadingRegistryImpl.INSTANCE
        for(Field f : ModelLoadingRegistryImpl)
    }
*/

    private BlockRenderManager manager = MinecraftClient.getInstance().getBlockRenderManager();

    private ConcreteMixerEntityModel model = new ConcreteMixerEntityModel();

    boolean printed = false;

    @Override
    public void render(ConcreteMixerBlockEntity blockEntity, double x, double y, double z, float partialTicks, int destroyStage) {
        if(manager == null){
            manager = MinecraftClient.getInstance().getBlockRenderManager();
        }



        GlStateManager.pushMatrix();
        GlStateManager.translated(x+.5, y, z+.5); //move rotation axis to the middle of our block
        GlStateManager.rotatef((blockEntity.getWorld().getTime() + partialTicks) * 4, 0, 1, 0);


        BlockState blockState = blockEntity.getCachedState();
        Tessellator tessellator_1 = Tessellator.getInstance();
        BufferBuilder bufferBuilder_1 = tessellator_1.getBufferBuilder();

        this.bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
        GuiLighting.disable();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableBlend();
        GlStateManager.disableCull();


        if (MinecraftClient.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(7425);
        } else {
            GlStateManager.shadeModel(7424);
        }

        //hard disable ambient occlusion, it doesn't work since we rotate our model.
        GlStateManager.shadeModel(7424);

        BlockModelRenderer.enableBrightnessCache();

        bufferBuilder_1.begin(7, VertexFormats.POSITION_COLOR_UV_LMAP);
        bufferBuilder_1.setOffset(-blockEntity.getPos().getX()-.5,-blockEntity.getPos().getY(),-blockEntity.getPos().getZ()-.5);


        BakedModel custom = VanillaReflavouredClient.TEST_MODEL;
        BakedModel model = manager.getModel(Blocks.JUKEBOX.getDefaultState());

        //the boolean argument is for culling, we don't want that
        //we need a custom tesselate since rotating stuff messes with the shadows. Who would have thought...
        ((BlockModelAvgBrightnessRenderer)(Object)this.manager.getModelRenderer()).tesselateFlatMaxBrightness(blockEntity.getWorld(), model, blockState, blockEntity.getPos(), bufferBuilder_1, false, new Random(), blockState.getRenderingSeed(blockEntity.getPos()));

        bufferBuilder_1.setOffset(0.0D, 0.0D, 0.0D);
        tessellator_1.draw();


        BlockModelRenderer.disableBrightnessCache();
        GuiLighting.enable();

        GlStateManager.popMatrix();

    }

    private void animate(ConcreteMixerBlockEntity blockEntity, float partialTicks, ChestEntityModel chestEntityModel_1) {
        //float float_2 = (blockEntity).getAnimationProgress(float_1);
        //float_2 = 1.0F - float_2;
        //float_2 = 1.0F - float_2 * float_2 * float_2;
        //chestEntityModel_1.method_2798().pitch = -(float_2 * 1.5707964F);
    }





}
