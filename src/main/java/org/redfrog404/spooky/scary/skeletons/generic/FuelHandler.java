package org.redfrog404.spooky.scary.skeletons.generic;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {

		Item fuelObject;

		if (fuel.getItem() instanceof ItemBlock) {
			fuelObject = Item.getItemFromBlock(Block
					.getBlockFromItem((ItemBlock) fuel.getItem()));
		} else {
			fuelObject = fuel.getItem();
		}

		if (fuelObject == Spooky.bone3) {
			return 40000;
		} else if (fuelObject == Items.blaze_powder) {
			return 1200;
		} else if (fuelObject == Item.getItemFromBlock(Blocks.netherrack)) {
			return 50;
		} else {
			return 0;
		}
	}

}