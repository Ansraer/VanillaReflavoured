package ansraer.reflavoured.common.blocks;

import ansraer.reflavoured.VanillaReflavoured;
import ansraer.reflavoured.common.items.PainterItem;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ReflavouredBlocks {

    public static Block CONCRETE_MIXER;

    public static void registerBlocks(){
        CONCRETE_MIXER = register("concrete_mixer", new Block(FabricBlockSettings.of(Material.METAL).build()));
    }




    private static Block register(String name, Block block){
        block = Registry.register(Registry.BLOCK, new Identifier(VanillaReflavoured.MODID, name), block);
        Registry.register(Registry.ITEM, new Identifier("tutorial", "example_block"), new BlockItem(block, new Item.Settings().group(ItemGroup.MISC)));
        return block;
    }
}
