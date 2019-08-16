package ansraer.reflavoured.mixin.client;

import ansraer.reflavoured.VanillaReflavoured;
import ansraer.reflavoured.VanillaReflavouredClient;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.ModelRotation;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {

    boolean addingFirstModel = true;

    @Shadow
    abstract void addModel(ModelIdentifier modelIdentifier_1);

    @Inject(at=@At("RETURN"), method = "<init>")
    public void onInit(CallbackInfo info){

        System.out.println("mixin into the model loader");

        ModelLoader loader = ((ModelLoader) (Object) this);

        ModelIdentifier id = new ModelIdentifier(new Identifier(VanillaReflavoured.MODID, "block/test_model"), "#");



        VanillaReflavouredClient.MODEL_LOADER_INSTANCE = ((ModelLoader) (Object) this);


        UnbakedModel unbaked = loader.getOrLoadModel(id);
        VanillaReflavouredClient.TEST_MODEL = loader.bake(id, ModelRotation.X0_Y0);
        System.out.println("baked model");
    }

    //we need to add our stuff at the beginning of the constructor
    @Inject(at=@At("RETURN"), method = "addModel")
    void addModel(ModelIdentifier modelIdentifier_1, CallbackInfo ci) {
        if(addingFirstModel){
            addingFirstModel = false;
            ModelIdentifier id = new ModelIdentifier(new Identifier(VanillaReflavoured.MODID, "block/test_model"), "#");
            addModel(id);
        }
    }

}
