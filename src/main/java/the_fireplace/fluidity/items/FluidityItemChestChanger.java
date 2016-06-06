package the_fireplace.fluidity.items;

import cpw.mods.ironchest.IronChest;
import cpw.mods.ironchest.IronChestType;
import cpw.mods.ironchest.TileEntityIronChest;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import the_fireplace.fluidity.Fluidity;
import the_fireplace.fluidity.compat.FluidityIronChests;
import the_fireplace.fluidity.entity.tile.TileEntityFluidityIronChest;
import the_fireplace.fluidity.enums.FluidityChestChangerType;
import the_fireplace.fluidity.enums.FluidityIronChestChangerType;
import the_fireplace.fluidity.enums.FluidityIronChestType;
import the_fireplace.fluidity.enums.IronFluidityChestChangerType;

public class FluidityItemChestChanger extends Item
{
	/**
	 * The changer type to use. 0 is Fluidity to Fluidity, 1 is Fluidity to Iron, 2 is Iron to Fluidity
	 */
	private byte useType;
	private FluidityChestChangerType type;
	private FluidityIronChestChangerType type2;
	private IronFluidityChestChangerType type3;

	public FluidityItemChestChanger(FluidityChestChangerType type)
	{
		this.type = type;
		this.useType = 0;

		this.setMaxStackSize(1);
		this.setUnlocalizedName("fluidity:" + type.name());
		this.setCreativeTab(Fluidity.tabFluidity);
	}

	public FluidityItemChestChanger(FluidityIronChestChangerType type)
	{
		this.type2 = type;
		this.useType = 1;

		this.setMaxStackSize(1);
		this.setUnlocalizedName("fluidity:" + type.name());
		this.setCreativeTab(Fluidity.tabFluidity);
	}

	public FluidityItemChestChanger(IronFluidityChestChangerType type)
	{
		this.type3 = type;
		this.useType = 2;

		this.setMaxStackSize(1);
		this.setUnlocalizedName("fluidity:" + type.name());
		this.setCreativeTab(Fluidity.tabFluidity);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return String.format(I18n.translateToLocal("fluidity.upgrade"), I18n.translateToLocal("fluidity."+this.getSourceName().toLowerCase()), I18n.translateToLocal("fluidity."+this.getTargetName().toLowerCase()));
	}

	@Override
	public EnumActionResult onItemUse (ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
			return EnumActionResult.PASS;
		switch(useType){
		case 0:{
			if(world.getBlockState(pos) != FluidityIronChests.fluidityChest.getStateFromMeta(FluidityIronChestType.valueOf(type.sourcename.toUpperCase()).ordinal())){
				return EnumActionResult.PASS;
			}
			TileEntity te = world.getTileEntity(pos);
			TileEntityFluidityIronChest newchest = new TileEntityFluidityIronChest();
			ItemStack[] chestContents = new ItemStack[27];
			if (te != null)
			{
				if (te instanceof TileEntityFluidityIronChest)
				{
					chestContents = ((TileEntityFluidityIronChest) te).chestContents;
					newchest = FluidityIronChestType.makeEntity(this.getTargetChestOrdinal(this.type.ordinal()));
					if (newchest == null)
						return EnumActionResult.PASS;
				}
			}

			te.updateContainingBlockInfo();
			if (te instanceof TileEntityChest)
				((TileEntityChest) te).checkForAdjacentChests();

			world.removeTileEntity(pos);
			world.setBlockToAir(pos);

			world.setTileEntity(pos, newchest);
			IBlockState state = FluidityIronChests.fluidityChest.getStateFromMeta(newchest.getType().ordinal());
			world.setBlockState(pos, state, 3);

			world.notifyBlockUpdate(pos, state, state, 3);

			TileEntity te2 = world.getTileEntity(pos);
			if (te2 instanceof TileEntityFluidityIronChest)
			{
				((TileEntityFluidityIronChest) te2).setContents(chestContents);
			}

			stack.stackSize = player.capabilities.isCreativeMode ? stack.stackSize : stack.stackSize - 1;
			return EnumActionResult.SUCCESS;}
		case 1:{
			if(world.getBlockState(pos) != FluidityIronChests.fluidityChest.getStateFromMeta(FluidityIronChestType.valueOf(type2.sourcename.toUpperCase()).ordinal())){
				return EnumActionResult.PASS;
			}
			TileEntity te = world.getTileEntity(pos);
			TileEntityIronChest newchest = new TileEntityIronChest();
			ItemStack[] chestContents = new ItemStack[27];
			if (te != null)
			{
				if (te instanceof TileEntityFluidityIronChest)
				{
					chestContents = ((TileEntityFluidityIronChest) te).chestContents;
					newchest = IronChestType.VALUES[getTargetChestOrdinal(this.type2.ordinal())].makeEntity();
					if (newchest == null)
						return EnumActionResult.PASS;
				}
			}

			te.updateContainingBlockInfo();
			if (te instanceof TileEntityChest)
				((TileEntityChest) te).checkForAdjacentChests();

			world.removeTileEntity(pos);
			world.setBlockToAir(pos);

			world.setTileEntity(pos, newchest);
			IBlockState state = IronChest.ironChestBlock.getStateFromMeta(newchest.getType().ordinal());
			world.setBlockState(pos, state, 3);

			world.notifyBlockUpdate(pos, state, state, 3);

			TileEntity te2 = world.getTileEntity(pos);
			if (te2 instanceof TileEntityIronChest)
			{
				((TileEntityIronChest) te2).setContents(chestContents);
			}

			stack.stackSize = player.capabilities.isCreativeMode ? stack.stackSize : stack.stackSize - 1;
			return EnumActionResult.SUCCESS;}
		case 2:{
			if(type3.getSource() == IronChestType.WOOD){
				if(!(world.getBlockState(pos).getBlock() instanceof BlockChest)){
					return EnumActionResult.PASS;
				}
			}else{
				if(world.getBlockState(pos) != IronChest.ironChestBlock.getStateFromMeta(IronChestType.valueOf(type3.sourcename.toUpperCase()).ordinal())){
					return EnumActionResult.PASS;
				}
			}
			TileEntity te = world.getTileEntity(pos);
			TileEntityFluidityIronChest newchest = new TileEntityFluidityIronChest();
			ItemStack[] chestContents = new ItemStack[27];
			if (te != null)
			{
				if (te instanceof TileEntityIronChest)
				{
					chestContents = ((TileEntityIronChest) te).chestContents;
					newchest = FluidityIronChestType.makeEntity(this.getTargetChestOrdinal(this.type3.ordinal()));
					if (newchest == null)
						return EnumActionResult.PASS;
				}else if (te instanceof TileEntityChest){
					if (((TileEntityChest) te).numPlayersUsing > 0)
						return EnumActionResult.PASS;
					if (!type3.canUpgrade(IronChestType.WOOD))
						return EnumActionResult.PASS;
					chestContents = new ItemStack[((TileEntityChest) te).getSizeInventory()];
					for (int i = 0; i < chestContents.length; i++)
						chestContents[i] = ((TileEntityChest) te).getStackInSlot(i);
					newchest = FluidityIronChestType.makeEntity(this.getTargetChestOrdinal(this.type3.ordinal()));
				}
			}

			te.updateContainingBlockInfo();
			if (te instanceof TileEntityChest)
				((TileEntityChest) te).checkForAdjacentChests();

			world.removeTileEntity(pos);
			world.setBlockToAir(pos);

			world.setTileEntity(pos, newchest);
			IBlockState state = FluidityIronChests.fluidityChest.getStateFromMeta(newchest.getType().ordinal());
			world.setBlockState(pos, state, 3);

			world.notifyBlockUpdate(pos, state, state, 3);

			TileEntity te2 = world.getTileEntity(pos);
			if (te2 instanceof TileEntityFluidityIronChest)
			{
				((TileEntityFluidityIronChest) te2).setContents(chestContents);
			}

			stack.stackSize = player.capabilities.isCreativeMode ? stack.stackSize : stack.stackSize - 1;
			return EnumActionResult.SUCCESS;}
		}
		return EnumActionResult.FAIL;
	}

	public int getTargetChestOrdinal(int sourceOrdinal)
	{
		switch(useType){
		case 0:
			return type.getTarget();
		case 1:
			return type2.getTarget();
		case 2:
			return type3.getTarget();
		}
		return 0;
	}
	public String getSourceName(){
		switch(useType){
		case 0:
			return type.sourcename;
		case 1:
			return type2.sourcename;
		case 2:
			return type3.sourcename;
		}
		return null;
	}
	public String getTargetName(){
		switch(useType){
		case 0:
			return type.targetname;
		case 1:
			return type2.targetname;
		case 2:
			return type3.targetname;
		}
		return null;
	}
}
