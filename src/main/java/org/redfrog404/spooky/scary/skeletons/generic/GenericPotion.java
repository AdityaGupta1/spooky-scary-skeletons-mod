package org.redfrog404.spooky.scary.skeletons.generic;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class GenericPotion extends Potion {
	
	public GenericPotion(int par1, ResourceLocation par2, boolean isBadEffect, int par4) {
		super(par1, par2, isBadEffect, par4);
	}
	
	public Potion setIconIndex(int par1, int par2)
    {
        super.setIconIndex(par1, par2);
        return this;
    }
}