package org.redfrog404.spooky.scary.skeletons.tools;

import net.minecraft.item.ItemPickaxe;

import org.redfrog404.spooky.scary.skeletons.generic.Spooky;

public class GenericPickaxe extends ItemPickaxe {

	public GenericPickaxe(String name, ToolMaterial material) {
		super(material);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.tools);
	}

}
