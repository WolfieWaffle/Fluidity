package the_fireplace.fluidity.compat;

import com.Red.main.ingame.RedBlocks;

import cyano.basemetals.registry.CrusherRecipeRegistry;
import net.minecraft.item.ItemStack;

public class BaseMetalsBreadstone implements IModCompat {


	@Override
	public void preInit() {
	}

	@Override
	public void init() {
		ItemStack flourStack5 = new ItemStack(FluidityBreadstone.flour, 5);
		ItemStack breadStoneStack = new ItemStack(RedBlocks.breadore);
		CrusherRecipeRegistry.addNewCrusherRecipe(breadStoneStack, flourStack5);
	}

	@Override
	public void registerInvRenderers() {
	}

}
