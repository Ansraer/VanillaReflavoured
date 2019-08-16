package ansraer.reflavoured.common.recipes;

import ansraer.reflavoured.common.item.PainterItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class PainterDyeRecipe extends SpecialCraftingRecipe {

    public PainterDyeRecipe(Identifier identifier_1) {
        super(identifier_1);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        boolean painterItem = false;
        boolean dyeItem = false;

        for(int int_1 = 0; int_1 < craftingInventory.getInvSize(); ++int_1) {
            ItemStack itemStack = craftingInventory.getInvStack(int_1);
            if (!itemStack.isEmpty()) {
                if (itemStack.getItem() instanceof PainterItem) {
                    if (painterItem) {
                        //we already have this, we don't need it twice, recipe failed.
                        return false;
                    }
                    painterItem = true;
                } else if (itemStack.getItem()instanceof DyeItem) {
                    if(dyeItem) {
                        //we already have this, we don't need it twice, recipe failed.
                        return false;
                    }
                    dyeItem = true;
                } else {
                    //somehow we got another type of item -> recipe failed
                    return false;
                }
            }
        }
        //if we have both item exactly once we are happy!
        return dyeItem && painterItem;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        DyeItem dyeItem = null;
        PainterItem painterItem = null;
        for(int int_1 = 0; int_1 < craftingInventory.getInvSize(); ++int_1) {
            ItemStack itemStack = craftingInventory.getInvStack(int_1);
            if (!itemStack.isEmpty()) {
                if(itemStack.getItem() instanceof  DyeItem){
                    dyeItem = (DyeItem) itemStack.getItem();
                } else if(itemStack.getItem() instanceof PainterItem){
                    painterItem = (PainterItem) itemStack.getItem();
                }
            }
        }

        ItemStack result = new ItemStack(painterItem);
        painterItem.setColor(dyeItem.getColor(), result);
        return result;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ReflavouredRecipes.PAINTER_DYE;
    }
}
