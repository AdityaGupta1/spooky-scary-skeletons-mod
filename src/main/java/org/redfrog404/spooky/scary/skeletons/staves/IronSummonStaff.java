package org.redfrog404.spooky.scary.skeletons.staves;

import java.util.List;

import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class IronSummonStaff extends GenericStaff {

	public IronSummonStaff(String name, Item ammunition) {
		super(name, ammunition);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List tooltip, boolean advanced) {
		tooltip.add(EnumChatFormatting.DARK_RED
				+ "Usage: Summons an Iron Golem minion");
		tooltip.add(EnumChatFormatting.DARK_RED
				+ "at a lesser cost than normal");

		super.addInformation(stack, playerIn, tooltip, advanced);
	}

	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player) {
		
		super.onItemRightClick(stack, world, player);
		
		EntityIronGolem golem = new EntityIronGolem(world);
		golem.setPosition(player.posX, player.posY, player.posZ);
		
		if (!world.isRemote) {
			world.spawnEntityInWorld(golem);
		}
		
		return stack;
	}

}
