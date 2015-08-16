package org.redfrog404.spooky.scary.skeletons.generic;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import org.redfrog404.spooky.scary.skeletons.entity.EntityJellySkull;

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

	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) {
		if (event.entityLiving.isPotionActive(Spooky.curse)) {
			PotionEffect curse = event.entityLiving
					.getActivePotionEffect(Spooky.curse);
			int level = curse == null ? 0 : curse.getAmplifier();

			if (level == 0) {
				return;
			}

			if (event.entityLiving.worldObj.rand.nextInt(5) == 0) {
				event.entityLiving
						.attackEntityFrom(DamageSource.generic, level);
			}
		}
	}

	@SubscribeEvent
	public void applyCurseOnJellySkullAttack(LivingAttackEvent event) {
		Entity entity = event.source.getEntity();
		
		if (entity == null) {
			return;
		}

		if (!(entity instanceof EntityJellySkull)) {
			return;
		}

		event.entityLiving.addPotionEffect(new PotionEffect(32, 3,
				((EntityJellySkull) entity).getSlimeSize()));
	}
	
	@SubscribeEvent
	public void makeJumpHigher(LivingJumpEvent event){
		if (!(event.entityLiving instanceof EntityPlayer)) {
			return;
		}
		
		EntityPlayer player = (EntityPlayer) event.entityLiving;
		
		if (player.getEquipmentInSlot(1) == null) {
			return;
		}
		
		if (player.getEquipmentInSlot(1).getItem() != Spooky.slime_boots) {
			return;
		}
		
		player.motionY += 0.5;
	}
}
