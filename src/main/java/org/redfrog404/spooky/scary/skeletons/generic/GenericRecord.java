package org.redfrog404.spooky.scary.skeletons.generic;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;

public class GenericRecord extends ItemRecord {
	public GenericRecord(String name) {
		super(name);
		setCreativeTab(Spooky.misc);
		setUnlocalizedName(name);
		setMaxStackSize(1);
	}

	@Override
	public ResourceLocation getRecordResource(String name) {
		return new ResourceLocation("spooky", name);
	}
}