package ansraer.reflavoured.common.block;

import ansraer.reflavoured.VanillaReflavoured;
import ansraer.reflavoured.common.block.entity.ConcreteMixerBlockEntity;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class ReflavouredBlocks {

    public static Block CONCRETE_MIXER;
    public static BlockEntityType<ConcreteMixerBlockEntity> CONCRETE_MIXER_BLOCK_ENTITY;

    public static void registerBlocks(){
        CONCRETE_MIXER = register("concrete_mixer", new BlockEntityBlock(FabricBlockSettings.of(Material.METAL).build(), ConcreteMixerBlockEntity::new));
        CONCRETE_MIXER_BLOCK_ENTITY = register("concrete_mixer",ConcreteMixerBlockEntity::new,CONCRETE_MIXER);
    }




    private static Block register(String name, Block block){
        block = Registry.register(Registry.BLOCK, new Identifier(VanillaReflavoured.MODID, name), block);
        Registry.register(Registry.ITEM, new Identifier(VanillaReflavoured.MODID, name), new BlockItem(block, new Item.Settings().group(ItemGroup.MISC)));
        return block;
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, Supplier<T> entity, Block block){
        return Registry.register(Registry.BLOCK_ENTITY,  new Identifier(VanillaReflavoured.MODID, name), BlockEntityType.Builder.create(entity, block).build(null));
    }
}
