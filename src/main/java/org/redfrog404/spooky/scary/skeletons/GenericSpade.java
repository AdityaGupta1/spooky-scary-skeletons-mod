package org.redfrog404.spooky.scary.skeletons;

import net.minecraft.item.ItemSpade;
import net.minecraft.item.Item.ToolMaterial;

public class GenericSpade extends ItemSpade {
	
	public GenericSpade(String name, ToolMaterial material) {
		super(material);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.tools);
	}

}
