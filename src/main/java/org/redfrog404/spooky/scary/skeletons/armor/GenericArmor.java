package org.redfrog404.spooky.scary.skeletons.armor;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import org.redfrog404.spooky.scary.skeletons.generic.Spooky;

public class GenericArmor extends ItemArmor {

	String name;

	public GenericArmor(String name, ArmorMaterial material, int renderIndex,
			int armorType, String armorName) {
		super(material, renderIndex, armorType);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.armor);
		this.canRepair = true;
		this.name = armorName;
	}

	@Override
	public String getArmorTexture(ItemStack armor, Entity entity, int slot,
			String type) {
		if (name.equals("moss")) {
			if (armor.getItem() == Spooky.moss_leggings) {
				return "spooky:textures/armor/moss_armor_2.png";
			} else {
				return "spooky:textures/armor/moss_armor_1.png";
			}
		} else if (name.equals("slime")) {
			if (armor.getItem() == Spooky.slime_leggings) {
				return "spooky:textures/armor/slime_armor_2.png";
			} else {
				return "spooky:textures/armor/slime_armor_1.png";
			}
		} else if (name.equals("fire")) {
			if (armor.getItem() == Spooky.fire_leggings) {
				return "spooky:textures/armor/fire_armor_2.png";
			} else {
				return "spooky:textures/armor/fire_armor_1.png";
			}
		} else if (name.equals("ice")) {
			if (armor.getItem() == Spooky.ice_leggings) {
				return "spooky:textures/armor/ice_armor_2.png";
			} else {
				return "spooky:textures/armor/ice_armor_1.png";
			}
		} else if (name.equals("bedrock")) {
			if (armor.getItem() == Spooky.bedrock_leggings) {
				return "spooky:textures/armor/bedrock_armor_2.png";
			} else {
				return "spooky:textures/armor/bedrock_armor_1.png";
			}
		}
		
		return null;
	}
	
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        if (name.equals("moss")) {
        	return Spooky.bone6 == repair.getItem() ? true : super.getIsRepairable(toRepair, repair);
        } else if (name.equals("slime")) {
        	return Spooky.gray_gel == repair.getItem() ? true : super.getIsRepairable(toRepair, repair);
        } else if (name.equals("fire")) {
        	return Spooky.fire_ingot == repair.getItem() ? true : super.getIsRepairable(toRepair, repair);
        } else if (name.equals("ice")) {
        	return Spooky.ice_plate == repair.getItem() ? true : super.getIsRepairable(toRepair, repair);
        } else if (name.equals("bedrock")) {
        	return Spooky.bedrockium_ingot == repair.getItem() ? true : super.getIsRepairable(toRepair, repair);
        }
        
        return false;
    }
}