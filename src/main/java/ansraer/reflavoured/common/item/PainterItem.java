package ansraer.reflavoured.common.item;

import ansraer.reflavoured.common.config.ReflavouredConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.function.Consumer;

public class PainterItem extends Item {

    private static String[] supportedBlocks = {"concrete", "wool", "carpet"};
    private int stickColor;

    public PainterItem(Settings settings, int stickColor) {
        super(settings.maxDamageIfAbsent(12));
        this.stickColor = stickColor;
    }

    /**
     * Used to render different layers of the texture with different colors depending on the nbt values.
     */
    public int getLayerColor(ItemStack itemStack, int layer) {
        switch (layer){
            case 1:
                return this.stickColor;
            case 2:
                return getColor(itemStack);
            default:
                return -1;
        }
    }

    @Override
    public boolean canRepair(ItemStack itemStack_1, ItemStack itemStack_2) {
        //FIXME: use this to refill the dye?
        return false;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Text getName(ItemStack itemStack_1) {
        if(hasColor(itemStack_1)){
            return new TranslatableText(I18n.translate(this.getTranslationKey(itemStack_1)) +" ("+ I18n.translate("color.reflavoured."+getColorString(itemStack_1))+")");
        } else {
            return super.getName(itemStack_1);
        }
    }

    /**
     * Display all color variations in the tabs
     */
    @Override
    public void appendStacks(ItemGroup itemGroup, DefaultedList<ItemStack> defaultedList) {
        if (this.isIn(itemGroup)) {
            defaultedList.add(new ItemStack(this));
            if(ReflavouredConfig.getInstance().showAllPainterColors){
                for(DyeColor d : DyeColor.values()){
                    ItemStack stack = new ItemStack(this);
                    setColor(d, stack);
                    defaultedList.add(stack);
                }
            }
        }

    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext itemUsageContext_1) {
        System.out.println("USE ON BLOCK");

        ItemStack itemStack = itemUsageContext_1.getStack();
        String toolColor = getColorString(itemStack);
        World world = itemUsageContext_1.getWorld();
        BlockPos blockPos = itemUsageContext_1.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);


        // Abort if we can't get the blockstate we are using this on
        if(blockState==null) return ActionResult.PASS;

        Block block = blockState.getBlock();
        String name = Registry.BLOCK.getId(block).getPath();

        // we now have the block path, e.g.: grey_wool or orange_concrete. Make sure that it A) starts with a color and
        // B) end with "_supportedBlock" AND does not have the same color as our tool AND that our tool has a color
        if (hasColor(itemStack) && isPaintableBlock(name, toolColor)){
            System.out.println("has color and is paintable");
            BlockState paintedBlock = getPaintedBlock(block, toolColor);
            //abort if there is no block with the correct color
            if(paintedBlock == null || paintedBlock.getBlock() == Blocks.AIR) return ActionResult.PASS;

            PlayerEntity player = itemUsageContext_1.getPlayer();
            world.playSound(player, blockPos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isClient) {
                world.setBlockState(blockPos, paintedBlock, 11);
                if (player != null) {
                    itemUsageContext_1.getStack().damage(1, (PlayerEntity)player, (Consumer)((playerEntity_1x) -> {
                        PlayerEntity p = (PlayerEntity) playerEntity_1x;
                        p.sendToolBreakStatus(itemUsageContext_1.getHand());
                        ItemStack stack = p.getStackInHand(itemUsageContext_1.getHand());
                        removeColor(stack);
                        stack.increment(1); //increase stack size by 1 so that when it is decreased after breaking we still have an item leaft.
                    }));
                }
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    private BlockState getPaintedBlock(Block block, String toolColor) {
        BlockState result = null;
        Identifier blockID = Registry.BLOCK.getId(block);
        String path = blockID.getPath();

        for (DyeColor value : DyeColor.values()) {
            if(path.startsWith(value.getName())){
                String block_name = path.replace(value.getName()+"_","");
                result = Registry.BLOCK.get(new Identifier(blockID.getNamespace(), (toolColor+"_"+block_name))).getDefaultState();
            }
        }

        return result;
    }

    /**
     * Checks if the block with a certain name can be painted and does not currently have the same color as our painter.
     */
    private boolean isPaintableBlock(String path, String painterColor){
        if(!path.contains("_")) return false;

        for (DyeColor value : DyeColor.values()) {
            //make sure that our block name starts with a color
            if(path.startsWith(value.getName())){

                //make sure that the blockname (without color) is supported
                return Arrays.stream(supportedBlocks).anyMatch((path.replace(value.getName()+"_",""))::equalsIgnoreCase);
            }
        }

        return false;
    }





    //  NBT BASED COLOR STUFF:

    public void setColor(DyeColor color, ItemStack itemStack){
        if(color != null){
            itemStack.getOrCreateSubTag("display").putString("color", color.getName());
        }
    }

    public boolean hasColor(ItemStack itemStack){
        CompoundTag compoundTag_1 = itemStack.getSubTag("display");
        return compoundTag_1 != null && compoundTag_1.containsKey("color");
    }

    public static void removeColor(ItemStack itemStack){
        CompoundTag compoundTag_1 = itemStack.getSubTag("display");
        if (compoundTag_1 != null && compoundTag_1.containsKey("color")) {
            compoundTag_1.remove("color");
        }
    }

    public String getColorString(ItemStack itemStack){
        CompoundTag compoundTag_1 = itemStack.getSubTag("display");
        if (compoundTag_1 != null && compoundTag_1.containsKey("color")) {
            return  compoundTag_1.getString("color");
        } else {
            return "none";
        }
    }

    public int getColor(ItemStack itemStack){
        DyeColor color = DyeColor.byName(this.getColorString(itemStack), DyeColor.WHITE);
        return color.getMaterialColor().color;
    }

}
