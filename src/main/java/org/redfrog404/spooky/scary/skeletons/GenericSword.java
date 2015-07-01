package org.redfrog404.spooky.scary.skeletons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GenericSword extends ItemSword {

	public GenericSword(String name, ToolMaterial material) {
		super(material);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Spooky.weapons);
	}

}
