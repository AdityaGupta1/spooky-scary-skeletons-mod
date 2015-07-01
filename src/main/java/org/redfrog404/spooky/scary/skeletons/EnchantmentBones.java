package org.redfrog404.spooky.scary.skeletons;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentBones extends Enchantment {
	private static final String __OBFID = "CL_00000100";

	public EnchantmentBones(int enchID, ResourceLocation enchName,
			int enchWeight) {
		super(enchID, enchName, enchWeight, EnumEnchantmentType.WEAPON);
		this.setName("bones");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	public int getMinEnchantability(int enchantmentLevel) {
		return 20;
	}

	/**
	 * Returns the maximum value of enchantability nedded on the enchantment
	 * level passed.
	 */
	public int getMaxEnchantability(int enchantmentLevel) {
		return 50;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	public int getMaxLevel() {
		return 1;
	}

	@SubscribeEvent
	public void getBones(LivingAttackEvent event) {
		if (event.source.getEntity() == null) {
			return;
		}

		if (!(event.source.getEntity() instanceof EntityPlayer)) {
			return;
		}

		EntityPlayer player = (EntityPlayer) event.source.getEntity();

		if (player.getHeldItem() == null) {
			return;
		}

		int level = EnchantmentHelper.getEnchantmentLevel(
				Spooky.bones.effectId, player.getHeldItem());

		if (level < 1) {
			return;
		}

		if (!player.worldObj.isRemote) {
			event.entityLiving.dropItem(Items.bone, 1);
		}
	}
}