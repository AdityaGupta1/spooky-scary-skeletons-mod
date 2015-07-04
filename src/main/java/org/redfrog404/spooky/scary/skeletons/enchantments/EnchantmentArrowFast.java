package org.redfrog404.spooky.scary.skeletons.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemBow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.redfrog404.spooky.scary.skeletons.Spooky;

public class EnchantmentArrowFast extends Enchantment {
	public EnchantmentArrowFast(int enchID, ResourceLocation enchName,
			int enchWeight) {
		super(enchID, enchName, enchWeight, EnumEnchantmentType.BOW);
		this.setName("arrowFast");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	public int getMinEnchantability(int enchantmentLevel) {
		return 12 + (enchantmentLevel - 1) * 20;
	}

	/**
	 * Returns the maximum value of enchantability nedded on the enchantment
	 * level passed.
	 */
	public int getMaxEnchantability(int enchantmentLevel) {
		return this.getMinEnchantability(enchantmentLevel) + 25;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	public int getMaxLevel() {
		return 2;
	}

	@SubscribeEvent
	public void decreaseBowUseDuration(PlayerUseItemEvent.Start event) {
		if (event.item.getItem() == null) {
			return;
		}

		if (!(event.item.getItem() instanceof ItemBow)) {
			return;
		}

		int level = EnchantmentHelper.getEnchantmentLevel(Spooky.haste.effectId,
				event.item);

		switch (level) {
		case 2:
			event.duration -= 10;
			break;
		case 1:
			event.duration -= 5;
			break;
		default:
			break;
		}

	}
}