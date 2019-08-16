package ansraer.reflavoured;

import ansraer.reflavoured.client.render.block.concrete_mixer.ConcreteMixerBlockEntityRender;
import ansraer.reflavoured.common.block.entity.ConcreteMixerBlockEntity;
import ansraer.reflavoured.common.config.ReflavouredConfig;
import ansraer.reflavoured.common.item.PainterItem;
import ansraer.reflavoured.common.item.ReflavouredItems;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.ModelRotation;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class VanillaReflavouredClient implements ClientModInitializer, ModMenuApi {

    public static final Logger LOG = LogManager.getLogger(VanillaReflavouredClient.class);
    public static ModelLoader MODEL_LOADER_INSTANCE = null;
    public static BakedModel TEST_MODEL;

    @Override
    public void onInitializeClient() {
        LOG.info("Starting reflavoured client!");

        //Color Stuff

        ColorProviderRegistry.ITEM.register((itemStack, layer) -> {
            return ((PainterItem)itemStack.getItem()).getLayerColor(itemStack, layer);
        }, ReflavouredItems.WOODEN_PAINTER, ReflavouredItems.IRON_PAINTER, ReflavouredItems.GOLDEN_PAINTER);


        //TEST_MODEL = MODEL_LOADER_INSTANCE.bake(new Identifier(VanillaReflavoured.MODID, "block/test_model"), ModelRotation.X0_Y0);
        BlockEntityRendererRegistry.INSTANCE.register(ConcreteMixerBlockEntity.class, new ConcreteMixerBlockEntityRender());

    }










    // Mod Menu Stuff

    @Override
    public String getModId() {
        return VanillaReflavoured.MODID;
    }

    @Override
    public Optional<Supplier<Screen>> getConfigScreen(Screen screen) {
        LOG.info("Setting up deprecated ModMenu options support");
        return Optional.empty();
    }

    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
        LOG.info("Setting up ModMenu options support");
        return (screen) -> AutoConfig.getConfigScreen(ReflavouredConfig.class, screen).get();
    }

}
