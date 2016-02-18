package org.redfrog404.spooky.scary.skeletons.tools;

import java.util.List;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.redfrog404.spooky.scary.skeletons.generic.Spooky;

public class GenericBow extends ItemBow {
	public static final String[] bowPullIconNameArray = new String[] { "_0",
			"_1", "_2" };
	private Item arrow;
	private int arrows;
	private String name;
	private double damage;
	private Item bow;

	public GenericBow(Item bow, String name, int durability, Item arrow,
			double damage) {
		this.maxStackSize = 1;
		this.setUnlocalizedName(name);
		this.setMaxDamage(durability - 1);
		this.setCreativeTab(Spooky.bows);
		this.arrow = arrow;
		this.arrows = 1;
		this.name = name;
		this.damage = damage + 0.5D;
		this.bow = bow;
	}

	public GenericBow(Item bow, String name, int durability, Item arrow,
			double damage, int arrows) {
		this.maxStackSize = 1;
		this.setUnlocalizedName(name);
		this.setMaxDamage(durability - 1);
		this.setCreativeTab(Spooky.bows);
		this.arrow = arrow;
		this.arrows = arrows;
		this.name = name;
		this.damage = damage + 0.5D;
		this.bow = bow;
	}

	/**
	 * Called when the player stops using an Item (stops holding the right mouse
	 * button).
	 * 
	 * @param timeLeft
	 *            The amount of ticks left before the using would have been
	 *            complete
	 */
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn,
			EntityPlayer playerIn, int timeLeft) {
		for (int arrows = 0; arrows < this.arrows; arrows++) {
			int j = this.getMaxItemUseDuration(stack) - timeLeft;
			net.minecraftforge.event.entity.player.ArrowLooseEvent event = new net.minecraftforge.event.entity.player.ArrowLooseEvent(
					playerIn, stack, j);
			if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
				return;
			j = event.charge;

			boolean flag = playerIn.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(
							Enchantment.infinity.effectId, stack) > 0;

			if (flag || playerIn.inventory.hasItem(arrow)) {
				float f = (float) j / 20.0F;
				f = (f * f + f * 2.0F) / 3.0F;

				if ((double) f < 0.1D) {
					return;
				}

				if (f > 1.0F) {
					f = 1.0F;
				}

				EntityArrow entityarrow = new EntityArrow(worldIn, playerIn,
						f * 2.0F);

				if (f == 1.0F) {
					entityarrow.setIsCritical(true);
				}

				int k = EnchantmentHelper.getEnchantmentLevel(
						Enchantment.power.effectId, stack);

				if (k > 0) {
					entityarrow.setDamage(entityarrow.getDamage() + (double) k
							* 0.5D + damage);
				}

				int l = EnchantmentHelper.getEnchantmentLevel(
						Enchantment.punch.effectId, stack);

				if (l > 0) {
					entityarrow.setKnockbackStrength(l);
				}

				if (EnchantmentHelper.getEnchantmentLevel(
						Enchantment.flame.effectId, stack) > 0 || stack.getItem().equals(Spooky.fire_bow)) {
					entityarrow.setFire(100);
				}

				worldIn.playSoundAtEntity(playerIn, "random.bow", 1.0F, 1.0F
						/ (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

				if (arrows > 0) {
					entityarrow.canBePickedUp = 2;
				}

				if (flag) {
					entityarrow.canBePickedUp = 2;
				} else {
					playerIn.inventory.consumeInventoryItem(arrow);
				}

				if (!worldIn.isRemote) {
					worldIn.spawnEntityInWorld(entityarrow);
				}
			}
		}

		if (!playerIn.capabilities.isCreativeMode) {
			stack.damageItem(1, playerIn);
		}
	}

	/**
	 * Called when the player finishes using this Item (E.g. finishes eating.).
	 * Not called when the player stops using the Item before the action is
	 * complete.
	 */
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn,
			EntityPlayer playerIn) {
		return stack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn,
			EntityPlayer playerIn) {
		net.minecraftforge.event.entity.player.ArrowNockEvent event = new net.minecraftforge.event.entity.player.ArrowNockEvent(
				playerIn, itemStackIn);
		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
			return event.result;

		if (playerIn.capabilities.isCreativeMode
				|| playerIn.inventory.hasItem(arrow)) {
			playerIn.setItemInUse(itemStackIn,
					this.getMaxItemUseDuration(itemStackIn));
		}

		return itemStackIn;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based
	 * on material.
	 */
	public int getItemEnchantability() {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List tooltip, boolean advanced) {
		tooltip.add(EnumChatFormatting.DARK_RED + "Damage: " + (4 + damage)
				+ " hearts");
		tooltip.add(EnumChatFormatting.DARK_GREEN + "Arrows Shot: " + arrows);
		tooltip.add(EnumChatFormatting.AQUA + "Durability: "
				+ (this.getMaxDamage() + 1));
		tooltip.add(EnumChatFormatting.GOLD + "Ammunition: "
				+ arrow.getItemStackDisplayName(new ItemStack(arrow)));
	}

	@Override
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player,
			int useRemaining) {

		int usesRemaining = stack.getMaxItemUseDuration()
				- player.getItemInUseCount();

		ModelResourceLocation model = new ModelResourceLocation("spooky:"
				+ name, "inventory");

		if (stack.getItem() == this && player.getItemInUse() != null) {
			if (usesRemaining >= 18) {
				model = new ModelResourceLocation("spooky:" + name + "_2",
						"inventory");
			} else if (usesRemaining > 13) {
				model = new ModelResourceLocation("spooky:" + name + "_1",
						"inventory");
			} else if (usesRemaining > 0) {
				model = new ModelResourceLocation("spooky:" + name + "_0",
						"inventory");
			}
		}
		return model;
	}
}