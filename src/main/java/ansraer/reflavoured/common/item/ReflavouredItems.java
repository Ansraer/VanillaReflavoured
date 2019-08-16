package ansraer.reflavoured.common.item;

import ansraer.reflavoured.VanillaReflavoured;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ReflavouredItems {

    public static Item SMOKED_FLESH;
    public static Item WOODEN_PAINTER;
    public static Item IRON_PAINTER;
    public static Item GOLDEN_PAINTER;

    public static void registerItems(){
        SMOKED_FLESH = register("smoked_flesh",new Item((new Item.Settings()).group(ItemGroup.FOOD).food((new FoodComponent.Builder()).hunger(4).saturationModifier(0.1F).meat().build())));

        WOODEN_PAINTER = register("wooden_painter", new PainterItem((new Item.Settings()).group(ItemGroup.TOOLS).maxCount(1).maxDamage(8), MaterialColor.WOOD.color));
        IRON_PAINTER = register("iron_painter", new PainterItem((new Item.Settings()).group(ItemGroup.TOOLS).maxCount(1).maxDamage(12), MaterialColor.IRON.color));
        GOLDEN_PAINTER = register("golden_painter", new PainterItem((new Item.Settings()).group(ItemGroup.TOOLS).maxCount(1).maxDamage(16), MaterialColor.GOLD.color));
    }




    private static Item register(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(VanillaReflavoured.MODID, name), item);
    }

}
