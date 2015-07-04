package org.redfrog404.spooky.scary.skeletons.tools;

import net.minecraft.item.ItemAxe;

import org.redfrog404.spooky.scary.skeletons.generic.Spooky;

public class GenericAxe extends ItemAxe {
	
	public GenericAxe(String name, ToolMaterial material) {
		super(material);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.tools);
	}

}
