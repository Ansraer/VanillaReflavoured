package ansraer.reflavoured.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

import java.util.function.Supplier;

public class BlockEntityBlock extends Block implements BlockEntityProvider {


    private final Supplier<BlockEntity> blockEntitySupplier;

    public BlockEntityBlock(Settings block$Settings_1, Supplier<BlockEntity> blockEntitySupplier) {
        super(block$Settings_1);
        this.blockEntitySupplier = blockEntitySupplier;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return blockEntitySupplier.get();
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState_1) {
        return BlockRenderType.INVISIBLE;
    }

}
