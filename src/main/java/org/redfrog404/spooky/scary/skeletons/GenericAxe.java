package org.redfrog404.spooky.scary.skeletons;

import net.minecraft.item.ItemAxe;
import net.minecraft.item.Item.ToolMaterial;

public class GenericAxe extends ItemAxe {
	
	public GenericAxe(String name, ToolMaterial material) {
		super(material);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.tools);
	}

}
