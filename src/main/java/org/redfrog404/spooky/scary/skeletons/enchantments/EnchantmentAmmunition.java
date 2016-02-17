package org.redfrog404.spooky.scary.skeletons.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.redfrog404.spooky.scary.skeletons.generic.Spooky;

public class EnchantmentAmmunition extends Enchantment {

	public EnchantmentAmmunition(int enchID, ResourceLocation enchName,
			int enchWeight) {
		super(enchID, enchName, enchWeight, EnumEnchantmentType.ALL);
		this.setName("ammunition");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	public int getMinEnchantability(int enchantmentLevel) {
		return 100000;
	}

	/**
	 * Returns the maximum value of enchantability needed on the enchantment
	 * level passed.
	 */
	public int getMaxEnchantability(int enchantmentLevel) {
		return 1000000;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	public int getMaxLevel() {
		return 5;
	}
}