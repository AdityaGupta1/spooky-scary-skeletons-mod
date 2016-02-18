package org.redfrog404.spooky.scary.skeletons.generic;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import org.redfrog404.spooky.scary.skeletons.entity.entity.EntityIncinerator;
import org.redfrog404.spooky.scary.skeletons.entity.entity.EntityJellySkull;
import org.redfrog404.spooky.scary.skeletons.entity.entity.EntityJuggernaut;
import org.redfrog404.spooky.scary.skeletons.entity.entity.EntityRisenDead;

public class EventHandlers {

	Random random = new Random();

	/*
	 * ========================================================================================================================================================================
	 * Potion Handlers
	 * ========================================================================================================================================================================
	 */

	@SubscribeEvent
	public void applyPotionEffects(LivingUpdateEvent event) {
		EntityLivingBase entity = event.entityLiving;

		if (entity.isPotionActive(Spooky.curse)) {
			PotionEffect curse = entity.getActivePotionEffect(Spooky.curse);
			int level = curse == null ? 0 : curse.getAmplifier();

			if (level == 0) {
				return;
			}

			if (entity.worldObj.rand.nextInt(5) == 0) {
				entity.attackEntityFrom(DamageSource.generic, level);
			}
		}

		if (entity.isPotionActive(Spooky.radioactive)) {
			PotionEffect radioactive = entity
					.getActivePotionEffect(Spooky.radioactive);
			int level = radioactive == null ? 0 : radioactive.getAmplifier();

			if (level == 0) {
				return;
			}

			if (entity.worldObj.rand.nextInt(5) == 0) {
				entity.attackEntityFrom(DamageSource.generic,
						random.nextInt(level));
			}
		}
	}

	@SubscribeEvent
	public void applyWeightEffect(LivingJumpEvent event) {
		EntityLivingBase entity = event.entityLiving;

		if (entity.isPotionActive(Spooky.weight)) {
			PotionEffect weight = entity
					.getActivePotionEffect(Spooky.weight);
			double level = weight == null ? 0 : weight.getAmplifier();

			if (level == 0) {
				return;
			}
			
			entity.motionY -= level/16;
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
	 * Armor
	 * ========================================================================================================================================================================
	 */

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

		entity.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 10));
		entity.extinguish();

	}

	@SubscribeEvent
	public void turnWaterIntoIce(LivingUpdateEvent event) {

		EntityLivingBase entity = event.entityLiving;

		if (entity.getEquipmentInSlot(1) == null) {
			return;
		}

		if (entity.getEquipmentInSlot(1).getItem() != Spooky.ice_boots) {
			return;
		}

		if (entity.getEquipmentInSlot(2) == null) {
			return;
		}

		if (entity.getEquipmentInSlot(2).getItem() != Spooky.ice_leggings) {
			return;
		}

		if (entity.getEquipmentInSlot(3) == null) {
			return;
		}

		if (entity.getEquipmentInSlot(3).getItem() != Spooky.ice_chestplate) {
			return;
		}

		if (entity.getEquipmentInSlot(4) == null) {
			return;
		}

		if (entity.getEquipmentInSlot(4).getItem() != Spooky.ice_helmet) {
			return;
		}

		double x = entity.posX;
		double y = entity.posY - 1;
		double z = entity.posZ;

		IBlockState water = Blocks.water.getBlockState().getBaseState();
		IBlockState ice = Blocks.ice.getBlockState().getBaseState();

		if (entity instanceof EntityPlayer) {
			if (entity.isSneaking()) {
				if (entity.worldObj.getBlockState(new BlockPos(x, y, z))
						.getBlock() != Blocks.ice) {
					return;
				}

				entity.worldObj.setBlockState(new BlockPos(x, y, z), water);
				entity.worldObj.setBlockState(new BlockPos(x + 1, y, z), water);
				entity.worldObj.setBlockState(new BlockPos(x, y, z + 1), water);
				entity.worldObj.setBlockState(new BlockPos(x - 1, y, z), water);
				entity.worldObj.setBlockState(new BlockPos(x, y, z - 1), water);
				entity.worldObj.setBlockState(new BlockPos(x + 1, y, z + 1),
						water);
				entity.worldObj.setBlockState(new BlockPos(x - 1, y, z - 1),
						water);
				entity.worldObj.setBlockState(new BlockPos(x + 1, y, z - 1),
						water);
				entity.worldObj.setBlockState(new BlockPos(x - 1, y, z + 1),
						water);
			}
		}

		if (entity.worldObj.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.water) {
			return;
		}

		entity.worldObj.setBlockState(new BlockPos(x, y, z), ice);
		entity.worldObj.setBlockState(new BlockPos(x + 1, y, z), ice);
		entity.worldObj.setBlockState(new BlockPos(x, y, z + 1), ice);
		entity.worldObj.setBlockState(new BlockPos(x - 1, y, z), ice);
		entity.worldObj.setBlockState(new BlockPos(x, y, z - 1), ice);
		entity.worldObj.setBlockState(new BlockPos(x + 1, y, z + 1), ice);
		entity.worldObj.setBlockState(new BlockPos(x - 1, y, z - 1), ice);
		entity.worldObj.setBlockState(new BlockPos(x + 1, y, z - 1), ice);
		entity.worldObj.setBlockState(new BlockPos(x - 1, y, z + 1), ice);

	}

	@SubscribeEvent
	public void slowEntityWithBedrockArmor(LivingUpdateEvent event) {
		EntityLivingBase entity = event.entityLiving;
		double slow = 0;

		if (entity.getEquipmentInSlot(1) != null) {
			if (entity.getEquipmentInSlot(1).getItem() == Spooky.bedrock_boots) {
				slow += 0.5;
			}
		}

		if (entity.getEquipmentInSlot(2) != null) {
			if (entity.getEquipmentInSlot(2).getItem() == Spooky.bedrock_leggings) {
				slow += 0.5;
			}
		}

		if (entity.getEquipmentInSlot(3) != null) {
			if (entity.getEquipmentInSlot(3).getItem() == Spooky.bedrock_chestplate) {
				slow += 0.5;
			}
		}

		if (entity.getEquipmentInSlot(4) != null) {
			if (entity.getEquipmentInSlot(4).getItem() == Spooky.bedrock_helmet) {
				slow += 0.5;
			}
		}
		
		int slowLevel = (int) Math.floor(slow);

		if (slowLevel < 1) {
			return;
		}

		entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 10,
				slowLevel - 1));
		
		if (slowLevel < 2) {
			return;
		}
		
		entity.addPotionEffect(new PotionEffect(Spooky.weight.id, 10));
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
	public void applyFireSword(PlayerTickEvent event) {
		EntityPlayer player = event.player;

		if (player.getHeldItem() == null) {
			return;
		}

		if (player.getHeldItem().getItem() != Spooky.greatsword) {
			return;
		}

		player.addPotionEffect(new PotionEffect(Potion.digSlowdown.getId(), 10,
				3));
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

		event.entityLiving.addPotionEffect(new PotionEffect(Spooky.curse
				.getId(), 60, ((EntityJellySkull) entity).getSlimeSize()));
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
		if (!(event.source.getEntity() instanceof EntityPlayer)) {
			return;
		}

		EntityPlayer entity = (EntityPlayer) event.source.getEntity();

		if (entity.getHeldItem() == null) {
			return;
		}

		if (entity.getHeldItem().getItem() != Spooky.radioactive_sword) {
			return;
		}

		event.entityLiving.addPotionEffect(new PotionEffect(26, random
				.nextInt(200) + 200, 1));
	}

	@SubscribeEvent
	public void noMetalFighting(LivingUpdateEvent event) {
		EntityLivingBase entity = event.entityLiving;
		if (entity instanceof EntityJuggernaut) {
			if (!(((EntityJuggernaut) entity).getAttackTarget() instanceof EntityIronGolem)) {
				return;
			}

			((EntityJuggernaut) entity).setAttackTarget(null);
		} else if (entity instanceof EntityIronGolem) {
			if (!(((EntityIronGolem) entity).getAttackTarget() instanceof EntityJuggernaut)) {
				return;
			}

			((EntityIronGolem) entity).setAttackTarget(null);
		} else {
			return;
		}
	}
}
