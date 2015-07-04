package org.redfrog404.spooky.scary.skeletons.generic;

import net.minecraft.item.Item;

public class GenericItem extends Item {

	public GenericItem(String name) {
		super();
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.materials);
	}

}
