package org.redfrog404.spooky.scary.skeletons;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemPickaxe;

public class GenericPickaxe extends ItemPickaxe {

	public GenericPickaxe(String name, ToolMaterial material) {
		super(material);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.tools);
	}

}
