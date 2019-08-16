package ansraer.reflavoured;

import ansraer.reflavoured.client.render.block.concrete_mixer.ConcreteMixerBlockEntityRender;
import ansraer.reflavoured.common.block.ReflavouredBlocks;
import ansraer.reflavoured.common.block.entity.ConcreteMixerBlockEntity;
import ansraer.reflavoured.common.config.ReflavouredConfig;
import ansraer.reflavoured.common.item.ReflavouredItems;
import ansraer.reflavoured.common.recipes.ReflavouredRecipes;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;

public class VanillaReflavoured implements ModInitializer {

	public static final String MODID = "reflavoured";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		//TODO: Disable furnace recipes for smoker stuff.

		System.out.println("Reflavoured starting!");
		AutoConfig.register(ReflavouredConfig.class, JanksonConfigSerializer::new);

		ReflavouredItems.registerItems();
		ReflavouredBlocks.registerBlocks();

		//BlockEntityRendererRegistry.INSTANCE.register(ConcreteMixerBlockEntity.class, new ConcreteMixerBlockEntityRender());


		ReflavouredRecipes.registerRecipes();
		ReflavouredRecipes.tweakRecipes();

	}
}
