package org.redfrog404.spooky.scary.skeletons.toolsandarmor;

import net.minecraft.item.ItemPickaxe;

import org.redfrog404.spooky.scary.skeletons.Spooky;

public class GenericPickaxe extends ItemPickaxe {

	public GenericPickaxe(String name, ToolMaterial material) {
		super(material);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.tools);
	}

}
