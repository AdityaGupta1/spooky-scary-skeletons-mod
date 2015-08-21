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

public class GenericStaff extends Item{
	
	private Item ammunition;
	
	public GenericStaff(String name, Item ammunition) {
		super();
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.staves);
		this.setMaxStackSize(1);
		this.ammunition = ammunition;
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if (!player.capabilities.isCreativeMode) {
			if (!player.inventory.hasItem(ammunition)) {
				return stack;
			}
			
			player.inventory.consumeInventoryItem(ammunition);
		}
		
		return stack;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List tooltip, boolean advanced) {
		tooltip.add(EnumChatFormatting.GOLD + "Ammunition: "
				+ ammunition.getItemStackDisplayName(new ItemStack(ammunition)));
	}

}
