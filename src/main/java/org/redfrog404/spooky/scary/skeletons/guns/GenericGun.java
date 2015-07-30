package org.redfrog404.spooky.scary.skeletons.guns;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.redfrog404.spooky.scary.skeletons.generic.Spooky;

public class GenericGun extends Item {

	private byte damage;
	private Item ammunition;
	private int bullets;

	public GenericGun(String name, int durability, Item bullet, byte damage) {
		super();
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.guns);
		this.setMaxStackSize(1);
		this.setMaxDamage(durability - 1);
		this.damage = damage;
		ammunition = bullet;
		this.bullets = 1;
	}
	
	public GenericGun(String name, int durability, Item bullet, byte damage, int bullets) {
		super();
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.guns);
		this.setMaxStackSize(1);
		this.setMaxDamage(durability - 1);
		this.damage = damage;
		ammunition = bullet;
		this.bullets = bullets;
	}

	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player) {
		for (int bullets = 0 ; bullets < this.bullets ; bullets++) {
			if (!player.capabilities.isCreativeMode) {
				if (!player.inventory.hasItem(ammunition)) {
					return stack;
				}

				player.inventory.consumeInventoryItem(ammunition);
			}

			world.playSoundAtEntity(player, "random.bow", 0.5F,
					0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (!world.isRemote) {
				EntityGenericBullet snowball = new EntityGenericBullet(world,
						player, damage);
				snowball.motionX *= 2;
				snowball.motionY *= 2;
				snowball.motionZ *= 2;
				world.spawnEntityInWorld(snowball);
			}
		}
		
		if (!player.capabilities.isCreativeMode) {
			if (this.getDamage(new ItemStack(this)) == this.getMaxDamage()) {
				--stack.stackSize;
			} else {
				stack.damageItem(1, player);
			}
		}
		
		return stack;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List tooltip, boolean advanced) {
		tooltip.add(EnumChatFormatting.DARK_RED + "Damage: " + ((float) damage / 2) + " hearts");
		tooltip.add(EnumChatFormatting.DARK_GREEN + "Bullets Shot: " + bullets);
		tooltip.add(EnumChatFormatting.AQUA + "Durability: " + (this.getMaxDamage() + 1));
		tooltip.add(EnumChatFormatting.GOLD + "Ammunition: "
				+ ammunition.getItemStackDisplayName(new ItemStack(ammunition))); 
	}

}
