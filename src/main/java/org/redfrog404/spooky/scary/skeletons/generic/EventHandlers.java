package org.redfrog404.spooky.scary.skeletons.generic;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.redfrog404.spooky.scary.skeletons.entity.EntityIncinerator;
import org.redfrog404.spooky.scary.skeletons.entity.EntityJellySkull;
import org.redfrog404.spooky.scary.skeletons.entity.EntityJuggernaut;
import org.redfrog404.spooky.scary.skeletons.entity.EntityRisenDead;

public class EventHandlers {

	Random random = new Random();

	/*
	 * ========================================================================================================================================================================
	 * Potion Handlers
	 * ========================================================================================================================================================================
	 */

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

		if (event.entityLiving.isPotionActive(Spooky.radioactive)) {
			PotionEffect radioactive = event.entityLiving
					.getActivePotionEffect(Spooky.radioactive);
			int level = radioactive == null ? 0 : radioactive.getAmplifier();

			if (level == 0) {
				return;
			}

			if (event.entityLiving.worldObj.rand.nextInt(5) == 0) {
				event.entityLiving.attackEntityFrom(DamageSource.generic,
						random.nextInt(level));
			}
		}
	}
	
	/*
	 * ========================================================================================================================================================================
	 * Boss Summoners
	 * ========================================================================================================================================================================
	 */
	
	@SubscribeEvent
	public void summonIncinerator(PlayerInteractEvent event) {
		EntityPlayer player = event.entityPlayer;

		if (event.action != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		if (player.getHeldItem() == null) {
			return;
		}

		if (player.getHeldItem().getItem() != Spooky.incinerator_summon) {
			return;
		}

		EntityIncinerator incinerator = new EntityIncinerator(event.world);
		incinerator.setLocationAndAngles(event.pos.getX(),
				event.pos.getY() + 1, event.pos.getZ(), 0, 0);

		if (!event.world.isRemote) {
			event.world.spawnEntityInWorld(incinerator);
		}

		if (!player.capabilities.isCreativeMode) {
			event.entityPlayer.inventory
					.consumeInventoryItem(Spooky.incinerator_summon);
		}
	}

	@SubscribeEvent
	public void summonJuggernaut(PlayerInteractEvent event) {
		EntityPlayer player = event.entityPlayer;

		if (event.action != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		if (player.getHeldItem() == null) {
			return;
		}

		if (player.getHeldItem().getItem() != Spooky.juggernaut_summon) {
			return;
		}

		EntityJuggernaut juggernaut = new EntityJuggernaut(event.world);
		juggernaut.setLocationAndAngles(event.pos.getX(), event.pos.getY() + 1,
				event.pos.getZ(), 0, 0);

		if (!event.world.isRemote) {
			event.world.spawnEntityInWorld(juggernaut);
		}

		if (!player.capabilities.isCreativeMode) {
			event.entityPlayer.inventory
					.consumeInventoryItem(Spooky.juggernaut_summon);
		}
	}

	/*
	 * ========================================================================================================================================================================
	 * Other Stuff
	 * ========================================================================================================================================================================
	 */
	
	@SubscribeEvent
	public void applyFireSword(LivingAttackEvent event) {
		if (event.source.getEntity() == null) {
			return;
		}

		if (!(event.source.getEntity() instanceof EntityLivingBase)) {
			return;
		}

		EntityLivingBase entity = (EntityLivingBase) event.source.getEntity();

		if (entity.getHeldItem() == null) {
			return;
		}

		if (entity.getHeldItem().getItem() != Spooky.fire_sword) {
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
	public void applyCurseOnJellySkullAttack(LivingAttackEvent event) {
		Entity entity = event.source.getEntity();

		if (entity == null) {
			return;
		}

		if (!(entity instanceof EntityJellySkull)) {
			return;
		}

		event.entityLiving.addPotionEffect(new PotionEffect(Spooky.curse.getId(), 3,
				((EntityJellySkull) entity).getSlimeSize()));
	}

	@SubscribeEvent
	public void makeJumpHigher(LivingJumpEvent event) {
		EntityLivingBase entity = (EntityLivingBase) event.entityLiving;

		if (entity.getEquipmentInSlot(1) == null) {
			return;
		}

		if (entity.getEquipmentInSlot(1).getItem() != Spooky.slime_boots) {
			return;
		}

		entity.motionY += 0.5;
	}

	@SubscribeEvent
	public void negateFallDamage(LivingFallEvent event) {
		EntityLivingBase entity = (EntityLivingBase) event.entityLiving;

		if (entity.getEquipmentInSlot(1) == null) {
			return;
		}

		if (entity.getEquipmentInSlot(1).getItem() != Spooky.slime_boots) {
			return;
		}

		event.setCanceled(true);
	}

	@SubscribeEvent
	public void dropCadmiumDust(BreakEvent event) {
		if (event.state.getBlock() != Spooky.zinc_ore) {
			return;
		}

		Random random = new Random();

		ItemStack stack = new ItemStack(Spooky.cadmium_dust);
		EntityItem item = new EntityItem(event.world, event.pos.getX(),
				event.pos.getY(), event.pos.getZ(), stack);

		if (random.nextInt(2) == 0) {
			event.world.spawnEntityInWorld(item);
		}
	}

	@SubscribeEvent
	public void applyFireAmulet(LivingAttackEvent event) {
		if (event.source.getEntity() == null) {
			return;
		}

		if (!(event.source.getEntity() instanceof EntityPlayer)) {
			return;
		}

		if (!((EntityPlayer) event.source.getEntity()).inventory
				.hasItem(Spooky.fire_amulet)) {
			return;
		}

		event.entity.setFire(3);
	}

	@SubscribeEvent
	public void transformBlock(PlayerInteractEvent event) {
		EntityPlayer player = event.entityPlayer;

		if (event.action != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		if (player.getHeldItem() == null) {
			return;
		}

		if (player.getHeldItem().getItem() != Spooky.fire_staff) {
			return;
		}

		if (player.worldObj.getBlockState(event.pos).getBlock() != Spooky.jade_block) {
			return;
		}

		if (!player.capabilities.isCreativeMode) {
			if (!player.inventory.hasItem(Spooky.fire_crystal)) {
				return;
			}

			player.inventory.consumeInventoryItem(Spooky.fire_crystal);
		}

		player.worldObj.setBlockState(event.pos, Spooky.fire_block
				.getBlockState().getBaseState());
		player.worldObj.createExplosion(player, event.pos.getX() + 0.5,
				event.pos.getY() + 0.5, event.pos.getZ() + 0.5, 1.5F, false);
	}

	@SubscribeEvent
	public void negateFireDamage(LivingUpdateEvent event) {

		EntityLivingBase entity = event.entityLiving;

		if (entity.getEquipmentInSlot(1) == null) {
			return;
		}

		if (entity.getEquipmentInSlot(1).getItem() != Spooky.fire_boots) {
			return;
		}

		if (entity.getEquipmentInSlot(2) == null) {
			return;
		}

		if (entity.getEquipmentInSlot(2).getItem() != Spooky.fire_leggings) {
			return;
		}

		if (entity.getEquipmentInSlot(3) == null) {
			return;
		}

		if (entity.getEquipmentInSlot(3).getItem() != Spooky.fire_chestplate) {
			return;
		}

		if (entity.getEquipmentInSlot(4) == null) {
			return;
		}

		if (entity.getEquipmentInSlot(4).getItem() != Spooky.fire_helmet) {
			return;
		}

		entity.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 160));
		entity.extinguish();

	}

	@SubscribeEvent
	public void summonIncineratorMinions(LivingAttackEvent event) {
		if (!(event.entityLiving instanceof EntityIncinerator)) {
			return;
		}

		Random random = new Random();

		if (random.nextInt(5) != 0) {
			return;
		}

		EntityLivingBase incinerator = event.entityLiving;

		EntityRisenDead minion = new EntityRisenDead(incinerator.worldObj);
		minion.setLocationAndAngles(incinerator.posX, incinerator.posY,
				incinerator.posZ, 0, 0);
		minion.setCurrentItemOrArmor(0, new ItemStack(Spooky.fire_sword));
		minion.setCurrentItemOrArmor(1, new ItemStack(Spooky.fire_boots));
		minion.setCurrentItemOrArmor(2, new ItemStack(Spooky.fire_leggings));
		minion.setCurrentItemOrArmor(3, new ItemStack(Spooky.fire_chestplate));
		minion.setCurrentItemOrArmor(4, new ItemStack(Spooky.fire_helmet));

		if (!incinerator.worldObj.isRemote) {
			incinerator.worldObj.spawnEntityInWorld(minion);
		}
	}

	@SubscribeEvent
	public void applyRadioactiveEffect(LivingAttackEvent event) {
		if (event.source.getEntity() == null) {
			return;
		}

		if (!(event.source.getEntity() instanceof EntityLivingBase)) {
			return;
		}

		EntityLivingBase entity = (EntityLivingBase) event.source.getEntity();

		if (entity.getHeldItem() == null) {
			return;
		}

		if (entity.getHeldItem().getItem() != Spooky.radioactive_sword
				|| entity.getHeldItem().getItem() != Spooky.radioactive_axe
				|| entity.getHeldItem().getItem() != Spooky.radioactive_pickaxe
				|| entity.getHeldItem().getItem() != Spooky.radioactive_spade) {
			return;
		}

		PotionEffect radioactive = new PotionEffect(Spooky.radioactive.getId(),
				random.nextInt(10) + 10);

		event.entityLiving.addPotionEffect(radioactive);
	}
}
