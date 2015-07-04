package org.redfrog404.spooky.scary.skeletons.generic;

import java.util.Random;

import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlers {

	@SubscribeEvent
	public void applyFireSword(LivingAttackEvent event) {
		if (event.source.getEntity() == null) {
			return;
		}

		if (!(event.source.getEntity() instanceof EntityPlayer)) {
			return;
		}

		EntityPlayer player = (EntityPlayer) event.source.getEntity();

		if (player.getHeldItem() == null) {
			return;
		}

		if (player.getHeldItem().getItem() != Spooky.fire_sword) {
			return;
		}

		event.entityLiving.setFire(10);
	}

	@SubscribeEvent
	public void dropItemsFromMobs(LivingDeathEvent event) {
		Random random = new Random();
		
		if (event.entityLiving instanceof EntityGuardian) {
			if (random.nextInt(25) == 1) {
				if (!event.entityLiving.worldObj.isRemote) {
					event.entityLiving.dropItem(Spooky.guardians_eye, 1);
				}
			}
		}
	}

}
