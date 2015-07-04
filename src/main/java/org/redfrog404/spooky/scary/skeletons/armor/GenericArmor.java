package org.redfrog404.spooky.scary.skeletons.armor;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import org.redfrog404.spooky.scary.skeletons.Spooky;

public class GenericArmor extends ItemArmor {

	public GenericArmor(String name, ArmorMaterial material, int renderIndex,
			int armorType) {
		super(material, renderIndex, armorType);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.armor);
	}

	@Override
	public String getArmorTexture(ItemStack armor, Entity entity, int slot,
			String type) {
		if (armor.getItem() == Spooky.moss_leggings) {
			return "spooky:models/armor/moss_armor_2.png";
		} else {
			return "spooky:models/armor/moss_armor_1.png";
		}
	}
}