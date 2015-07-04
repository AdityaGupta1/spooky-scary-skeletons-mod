package org.redfrog404.spooky.scary.skeletons.tools;

import net.minecraft.item.ItemSpade;

import org.redfrog404.spooky.scary.skeletons.generic.Spooky;

public class GenericSpade extends ItemSpade {
	
	public GenericSpade(String name, ToolMaterial material) {
		super(material);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.tools);
	}

}
