package org.redfrog404.spooky.scary.skeletons.staves;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.redfrog404.spooky.scary.skeletons.generic.Spooky;

public class TransformingStaff extends GenericStaff {

	private Item ammunition;

	public TransformingStaff(String name) {
		super(name, Spooky.fire_crystal);
		this.setUnlocalizedName(name);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List tooltip, boolean advanced) {
		tooltip.add(EnumChatFormatting.DARK_RED
				+ "Usage: Turns jade blocks into blocks of");
		tooltip.add(EnumChatFormatting.DARK_RED
				+ "pure flame, which can be mined and crafted");
		tooltip.add(EnumChatFormatting.DARK_RED + "into ingots");

		super.addInformation(stack, playerIn, tooltip, advanced);
	}

	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player) {
		return stack;
	}

}
