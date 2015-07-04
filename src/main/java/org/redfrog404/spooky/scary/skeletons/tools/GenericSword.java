package org.redfrog404.spooky.scary.skeletons.tools;

import net.minecraft.item.ItemSword;

import org.redfrog404.spooky.scary.skeletons.generic.Spooky;

public class GenericSword extends ItemSword {

	public GenericSword(String name, ToolMaterial material) {
		super(material);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.weapons);
	}

}
