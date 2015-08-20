package org.redfrog404.spooky.scary.skeletons.staves;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.redfrog404.spooky.scary.skeletons.generic.Spooky;

public class IncineratorStaff extends Item {

	//TODO Set the ammo to something that drops from the Incinerator
	private Item ammunition = Items.blaze_powder;

	public IncineratorStaff(String name) {
		super();
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.staves);
		this.setMaxStackSize(1);
	}

	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player) {
		
		if (!player.capabilities.isCreativeMode) {
			if (!player.inventory.hasItem(Items.blaze_powder)) {
				return stack;
			}
			
			player.inventory.consumeInventoryItem(Items.blaze_powder);
		}
		
		double yaw = Math.toRadians(player.rotationYaw);
		double pitch = Math.toRadians(player.rotationPitch);
		double dx = -Math.sin(yaw);
		double dy = -Math.sin(pitch);
		double dz = Math.cos(yaw);
		
		EntityIncineratorFireball fireball = new EntityIncineratorFireball(world, false);
		fireball.setLocationAndAngles(player.posX, player.posY, player.posZ, player.rotationPitch, player.rotationYaw);
		fireball.accelerationX = dx / 3;
		fireball.accelerationY = dy / 3;
		fireball.accelerationZ = dz / 3;
		fireball.moveEntity(dx, dy, dz);
		
		if (!world.isRemote) {
			world.spawnEntityInWorld(fireball);
		}
		
		return stack;

	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List tooltip, boolean advanced) {
		tooltip.add(EnumChatFormatting.DARK_RED
				+ "Usage: Summons a fireball that spawns a floating");
		tooltip.add(EnumChatFormatting.DARK_RED +  "lava orb when it contacts an entity or the ground");
		tooltip.add(EnumChatFormatting.GOLD + "Ammunition: "
				+ ammunition.getItemStackDisplayName(new ItemStack(ammunition)));
	}
}
