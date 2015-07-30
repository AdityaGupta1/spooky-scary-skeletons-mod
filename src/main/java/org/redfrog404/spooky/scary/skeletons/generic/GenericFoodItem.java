package org.redfrog404.spooky.scary.skeletons.generic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class GenericFoodItem extends ItemFood {
	
	public GenericFoodItem(String unlocalizedName, int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(Spooky.food);
    }

}
