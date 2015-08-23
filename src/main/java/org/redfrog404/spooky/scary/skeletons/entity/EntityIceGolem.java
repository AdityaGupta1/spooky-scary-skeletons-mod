package org.redfrog404.spooky.scary.skeletons.entity;

import org.redfrog404.spooky.scary.skeletons.biomes.BiomeRegistry;
import org.redfrog404.spooky.scary.skeletons.generic.Spooky;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.base.Predicate;

public class EntityIceGolem extends EntityGolem implements IMob {
	/** deincrements, and a distance-to-home check is done at 0 */
	private int homeCheckTimer;
	Village villageObj;
	private int attackTimer;
	private int holdRoseTick;

	public EntityIceGolem(World worldIn) {
		super(worldIn);
		this.setSize(1.4F, 2.9F);
		((PathNavigateGround) this.getNavigator()).func_179690_a(true);
		this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0D, true));
		this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
		this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWander(this, 0.6D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this,
				EntityPlayer.class, 6.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false,
				new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this,
				EntityPlayer.class, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this,
				EntityIronGolem.class, true));
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
				.setBaseValue(150.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
				.setBaseValue(0.25D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange)
				.setBaseValue(35.0D);
	}

	/**
	 * Decrements the entity's air supply when underwater
	 */
	protected int decreaseAirSupply(int p_70682_1_) {
		return p_70682_1_;
	}

	protected void collideWithEntity(Entity p_82167_1_) {
		if (p_82167_1_ instanceof IMob && this.getRNG().nextInt(20) == 0) {
			this.setAttackTarget((EntityLivingBase) p_82167_1_);
		}

		super.collideWithEntity(p_82167_1_);
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (this.attackTimer > 0) {
			--this.attackTimer;
		}

		if (this.holdRoseTick > 0) {
			--this.holdRoseTick;
		}

		if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7D
				&& this.rand.nextInt(5) == 0) {
			int i = MathHelper.floor_double(this.posX);
			int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
			int k = MathHelper.floor_double(this.posZ);
			IBlockState iblockstate = this.worldObj.getBlockState(new BlockPos(
					i, j, k));
			Block block = iblockstate.getBlock();

			if (block.getMaterial() != Material.air) {
				this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK,
						this.posX + ((double) this.rand.nextFloat() - 0.5D)
								* (double) this.width,
						this.getEntityBoundingBox().minY + 0.1D, this.posZ
								+ ((double) this.rand.nextFloat() - 0.5D)
								* (double) this.width,
						4.0D * ((double) this.rand.nextFloat() - 0.5D), 0.5D,
						((double) this.rand.nextFloat() - 0.5D) * 4.0D,
						new int[] { Block.getStateId(iblockstate) });
			}
		}
		
		if (this.getAttackTarget() instanceof EntityIceGolem) {
			this.setAttackTarget(null);
		}
	}
	
	public boolean getCanSpawnHere()
    {
        return this.func_180484_a(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) == 1.0F;
    }
	
	public float func_180484_a(BlockPos pos)
    {
        return this.worldObj.getBiomeGenForCoords(pos) == BiomeRegistry.ice ? 1.0F : 0.0F;
    }

	/**
	 * Returns true if this entity can attack entities of the specified class.
	 */
	public boolean canAttackClass(Class p_70686_1_) {
		return this.isPlayerCreated()
				&& EntityPlayer.class.isAssignableFrom(p_70686_1_) ? false
				: super.canAttackClass(p_70686_1_);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setBoolean("PlayerCreated", this.isPlayerCreated());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		this.setPlayerCreated(tagCompund.getBoolean("PlayerCreated"));
	}

	public boolean attackEntityAsMob(Entity p_70652_1_) {
		this.attackTimer = 10;
		this.worldObj.setEntityState(this, (byte) 4);
		boolean flag = p_70652_1_.attackEntityFrom(
				DamageSource.causeMobDamage(this),
				(float) (7 + this.rand.nextInt(15)));

		if (flag) {
			p_70652_1_.motionY += 0.4000000059604645D;
			this.func_174815_a(this, p_70652_1_);
		}

		this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
		return flag;
	}

	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte p_70103_1_) {
		if (p_70103_1_ == 4) {
			this.attackTimer = 10;
			this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
		} else if (p_70103_1_ == 11) {
			this.holdRoseTick = 400;
		} else {
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	public Village getVillage() {
		return this.villageObj;
	}

	@SideOnly(Side.CLIENT)
	public int getAttackTimer() {
		return this.attackTimer;
	}

	public void setHoldingRose(boolean p_70851_1_) {
		this.holdRoseTick = p_70851_1_ ? 400 : 0;
		this.worldObj.setEntityState(this, (byte) 11);
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound() {
		return "mob.irongolem.hit";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound() {
		return "mob.irongolem.death";
	}

	protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_) {
		this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
	}

	protected Item getDropItem() {
		return Spooky.ice_ingot;
	}
	
	/**
	 * Drop 0-2 items of this living's type
	 */
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
		int j = this.rand.nextInt(3);
		int k;

		k = 3 + this.rand.nextInt(3);

		for (int l = 0; l < k; ++l) {
			if (!this.worldObj.isRemote) {
				this.dropItem(Spooky.ice_ingot, 1);
			}
		}
	}

	public int getHoldRoseTick() {
		return this.holdRoseTick;
	}

	public boolean isPlayerCreated() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setPlayerCreated(boolean p_70849_1_) {
		byte b0 = this.dataWatcher.getWatchableObjectByte(16);

		if (p_70849_1_) {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 | 1)));
		} else {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & -2)));
		}
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	public void onDeath(DamageSource cause) {
		dropFewItems(true, 1);
		super.onDeath(cause);
	}

	static class AINearestAttackableTargetNonCreeper extends
			EntityAINearestAttackableTarget {
		private static final String __OBFID = "CL_00002231";

		public AINearestAttackableTargetNonCreeper(
				final EntityCreature p_i45858_1_, Class p_i45858_2_,
				int p_i45858_3_, boolean p_i45858_4_, boolean p_i45858_5_,
				final Predicate p_i45858_6_) {
			super(p_i45858_1_, p_i45858_2_, p_i45858_3_, p_i45858_4_,
					p_i45858_5_, p_i45858_6_);
			this.targetEntitySelector = new Predicate() {
				private static final String __OBFID = "CL_00002230";

				public boolean func_180096_a(EntityLivingBase p_180096_1_) {
					if (p_i45858_6_ != null && !p_i45858_6_.apply(p_180096_1_)) {
						return false;
					} else if (p_180096_1_ instanceof EntityCreeper) {
						return false;
					} else {
						if (p_180096_1_ instanceof EntityPlayer) {
							double d0 = AINearestAttackableTargetNonCreeper.this
									.getTargetDistance();

							if (p_180096_1_.isSneaking()) {
								d0 *= 0.800000011920929D;
							}

							if (p_180096_1_.isInvisible()) {
								float f = ((EntityPlayer) p_180096_1_)
										.getArmorVisibility();

								if (f < 0.1F) {
									f = 0.1F;
								}

								d0 *= (double) (0.7F * f);
							}

							if ((double) p_180096_1_
									.getDistanceToEntity(p_i45858_1_) > d0) {
								return false;
							}
						}

						return AINearestAttackableTargetNonCreeper.this
								.isSuitableTarget(p_180096_1_, false);
					}
				}

				public boolean apply(Object p_apply_1_) {
					return this.func_180096_a((EntityLivingBase) p_apply_1_);
				}
			};
		}
	}
}