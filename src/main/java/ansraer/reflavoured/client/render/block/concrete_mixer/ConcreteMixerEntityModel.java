package ansraer.reflavoured.client.render.block.concrete_mixer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Cuboid;
import net.minecraft.client.model.Model;

@Environment(EnvType.CLIENT)
public class ConcreteMixerEntityModel extends Model {

    private Cuboid base;

    public ConcreteMixerEntityModel(){
        this.base = (new Cuboid(this, 0, 19)).setTextureSize(64, 64);
        this.base.addBox(0.0F, 0.0F, 0.0F, 14, 10, 14, 0.0F);
        this.base.rotationPointX = 1.0F;
        this.base.rotationPointY = 6.0F;
        this.base.rotationPointZ = 1.0F;
    }

    public void render() {
        //this.hatch.pitch = this.lid.pitch;
        //this.lid.render(0.0625F);
        //this.hatch.render(0.0625F);
        this.base.render(0.0625F);
    }
}
