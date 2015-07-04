package org.redfrog404.spooky.scary.skeletons.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.redfrog404.spooky.scary.skeletons.Spooky;

public class EnchantmentPoison extends Enchantment {
	public EnchantmentPoison(int enchID, ResourceLocation enchName,
			int enchWeight) {
		super(enchID, enchName, enchWeight, EnumEnchantmentType.WEAPON);
		this.setName("poison");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	public int getMinEnchantability(int enchantmentLevel) {
		return 10 + 20 * (enchantmentLevel - 1);
	}

	/**
	 * Returns the maximum value of enchantability nedded on the enchantment
	 * level passed.
	 */
	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	public int getMaxLevel() {
		return 2;
	}

	@SubscribeEvent
	public void applyPoison(LivingAttackEvent event) {
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
		
		int level = EnchantmentHelper.getEnchantmentLevel(Spooky.poison.effectId,
				player.getHeldItem());

		switch (level) {
		case 2:
			event.entityLiving.addPotionEffect(new PotionEffect(19, 100, 1));
			break;
		case 1:
			event.entityLiving.addPotionEffect(new PotionEffect(19, 100, 0));
			break;
		default:
			break;
		}
	}
}