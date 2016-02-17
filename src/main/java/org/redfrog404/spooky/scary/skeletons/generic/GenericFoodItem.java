package org.redfrog404.spooky.scary.skeletons.generic;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GenericFoodItem extends ItemFood {

	String name = "";

	public GenericFoodItem(String unlocalizedName, int amount,
			float saturation, boolean isWolfFood, boolean isAlwaysEdible) {
		super(amount, saturation, isWolfFood);
		this.setUnlocalizedName(unlocalizedName);
		name = unlocalizedName;
		this.setCreativeTab(Spooky.food);
		if (isAlwaysEdible) {
			this.setAlwaysEdible();
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List tooltip, boolean advanced) {

		if (name.equals("candy")) {
			tooltip.add(EnumChatFormatting.WHITE + "Only 80% sugar!");
		} else if (name.equals("spicy_candy")) {
			tooltip.add(EnumChatFormatting.DARK_RED
					+ "Builds resistance to hot things.");
		} else if (name.equals("hard_candy")) {
			tooltip.add(EnumChatFormatting.GRAY
					+ "So hard that even a diamond pickaxe can't break it.");
		} else {
			return;
		}

	}

}
