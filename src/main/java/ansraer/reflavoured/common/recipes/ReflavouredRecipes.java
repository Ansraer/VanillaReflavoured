package ansraer.reflavoured.common.recipes;

import ansraer.reflavoured.VanillaReflavoured;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ReflavouredRecipes {

    //you need placholder json files to enable these recipes... see painter_dye.json
    public static SpecialRecipeSerializer<PainterDyeRecipe> PAINTER_DYE;

    public static void registerRecipes(){
        PAINTER_DYE = register("crafting_special_painterdye", new SpecialRecipeSerializer<PainterDyeRecipe>(PainterDyeRecipe::new));
    }

    public static void tweakRecipes() {


    }


    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String name, S recipeSerializer_1) {
        return Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(VanillaReflavoured.MODID, name), recipeSerializer_1);
    }
}
