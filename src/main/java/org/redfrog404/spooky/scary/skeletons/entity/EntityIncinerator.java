package org.redfrog404.spooky.scary.skeletons.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.redfrog404.spooky.scary.skeletons.generic.Spooky;
import org.redfrog404.spooky.scary.skeletons.staves.EntityIncineratorFireball;

public class EntityIncinerator extends EntityMob implements IBossDisplayData {

	@Override
	public IChatComponent getDisplayName() {
		super.getDisplayName();
		return new ChatComponentText("The Incinerator");
	}

	/**
	 * The attribute which determines the chance that this mob will spawn
	 * reinforcements
	 */
	protected static final IAttribute reinforcementChance = (new RangedAttribute(
			(IAttribute) null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D))
			.setDescription("Spawn Reinforcements Chance");
	private static final UUID babySpeedBoostUUID = UUID
			.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(
			babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);
	private final EntityAIBreakDoor breakDoor = new EntityAIBreakDoor(this);
	/**
	 * Ticker used to determine the time remaining for this zombie to convert
	 * into a villager when cured.
	 */
	private int conversionTime;
	private boolean field_146076_bu = false;
	/** The width of the entity */
	private float zombieWidth = -1.0F;
	/** The height of the the entity. */
	private float zombieHeight;

	public EntityIncinerator(World worldIn) {
		super(worldIn);
		((PathNavigateGround) this.getNavigator()).func_179688_b(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, this.field_175455_a);
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(7, new EntityIncinerator.AIFireballAttack());
		this.tasks.addTask(7, new EntityIncinerator.AILightningAttack());
		this.tasks.addTask(7, new EntityAIWatchClosest(this,
				EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.tasks.addTask(8, new EntityAIAttackOnCollide(this,
				EntityPlayer.class, 1.0D, false));
		this.applyEntityAI();
		this.setSize(1.2F, 3.9F);
		this.isImmuneToFire = true;
		this.setCurrentItemOrArmor(0, new ItemStack(Spooky.incinerator_staff));
		this.equipmentDropChances[0] = 0.0F;
	}

	protected void applyEntityAI() {
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true,
				new Class[] { EntityPigZombie.class }));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this,
				EntityPlayer.class, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this,
				EntityIronGolem.class, true));
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.followRange)
				.setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
				.setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
				.setBaseValue(12.0D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
				.setBaseValue(600.0F);
		this.getAttributeMap()
				.registerAttribute(reinforcementChance)
				.setBaseValue(
						this.rand.nextDouble()
								* net.minecraftforge.common.ForgeModContainer.zombieSummonBaseChance);
	}

	protected void entityInit() {
		super.entityInit();
		this.getDataWatcher().addObject(12, Byte.valueOf((byte) 0));
		this.getDataWatcher().addObject(13, Byte.valueOf((byte) 0));
		this.getDataWatcher().addObject(14, Byte.valueOf((byte) 0));
	}

	/**
	 * Returns the current armor value as determined by a call to
	 * InventoryPlayer.getTotalArmorValue
	 */
	public int getTotalArmorValue() {
		int i = super.getTotalArmorValue() + 2;

		if (i > 20) {
			i = 20;
		}

		return i;
	}

	public boolean func_146072_bX() {
		return this.field_146076_bu;
	}

	public void func_146070_a(boolean p_146070_1_) {
		if (this.field_146076_bu != p_146070_1_) {
			this.field_146076_bu = p_146070_1_;

			if (p_146070_1_) {
				this.tasks.addTask(1, this.breakDoor);
			} else {
				this.tasks.removeTask(this.breakDoor);
			}
		}
	}

	/**
	 * If Animal, checks if the age timer is negative
	 */
	public boolean isChild() {
		return this.getDataWatcher().getWatchableObjectByte(12) == 1;
	}

	/**
	 * Get the experience points the entity currently has.
	 */
	protected int getExperiencePoints(EntityPlayer player) {
		if (this.isChild()) {
			this.experienceValue = (int) ((float) this.experienceValue * 2.5F);
		}

		return super.getExperiencePoints(player);
	}

	/**
	 * Return whether this zombie is a villager.
	 */
	public boolean isVillager() {
		return false;
	}

	/**
	 * Set whether this zombie is a villager.
	 */
	public void setVillager(boolean villager) {
		this.getDataWatcher().updateObject(13, Byte.valueOf((byte) (0)));
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	public void onLivingUpdate() {
		BossStatus.setBossStatus(this, true);

		if (this.worldObj.isDaytime() && !this.worldObj.isRemote
				&& !this.isChild()) {
			float f = this.getBrightness(1.0F);
			BlockPos blockpos = new BlockPos(this.posX,
					(double) Math.round(this.posY), this.posZ);

			if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F
					&& this.worldObj.canSeeSky(blockpos)) {
				boolean flag = true;
				ItemStack itemstack = this.getEquipmentInSlot(4);

				if (itemstack != null) {
					if (itemstack.isItemStackDamageable()) {
						itemstack.setItemDamage(itemstack.getItemDamage()
								+ this.rand.nextInt(2));

						if (itemstack.getItemDamage() >= itemstack
								.getMaxDamage()) {
							this.renderBrokenItemStack(itemstack);
							this.setCurrentItemOrArmor(4, (ItemStack) null);
						}
					}

					flag = false;
				}

				if (flag) {
					this.addPotionEffect(new PotionEffect(Potion.weakness.id, 8));
				}
			}
		}

		if (this.isRiding() && this.getAttackTarget() != null
				&& this.ridingEntity instanceof EntityChicken) {
			((EntityLiving) this.ridingEntity).getNavigator().setPath(
					this.getNavigator().getPath(), 1.5D);
		}

		super.onLivingUpdate();
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (super.attackEntityFrom(source, amount)) {
			EntityLivingBase entitylivingbase = this.getAttackTarget();

			if (entitylivingbase == null
					&& source.getEntity() instanceof EntityLivingBase) {
				entitylivingbase = (EntityLivingBase) source.getEntity();
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		if (!this.worldObj.isRemote && this.isConverting()) {
			int i = this.getConversionTimeBoost();
			this.conversionTime -= i;

			if (this.conversionTime <= 0) {
				this.convertToVillager();
			}
		}

		super.onUpdate();
	}

	public boolean attackEntityAsMob(Entity p_70652_1_) {
		boolean flag = super.attackEntityAsMob(p_70652_1_);

		if (flag) {
			int i = this.worldObj.getDifficulty().getDifficultyId();

			if (this.getHeldItem() == null && this.isBurning()
					&& this.rand.nextFloat() < (float) i * 0.3F) {
				p_70652_1_.setFire(2 * i);
			}
		}

		return flag;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound() {
		return "mob.zombie.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound() {
		return "mob.zombie.hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound() {
		return "mob.zombie.death";
	}

	protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_) {
		this.playSound("mob.zombie.step", 0.15F, 1.0F);
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
		int j = this.rand.nextInt(3) + this.rand.nextInt(1 + p_70628_2_);
		int k;

		for (k = 0; k < j; ++k) {
			this.dropItem(Spooky.fire_crystal, 1);
		}

		j = this.rand.nextInt(3);

		if (j == 0) {
			this.dropItem(Spooky.incinerator_staff, 1);
		} else if (j == 1) {
			this.dropItem(Spooky.fire_amulet, 1);
		} else if (j == 2) {
			this.dropItem(Spooky.fire_staff, 1);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);

		if (this.isChild()) {
			tagCompound.setBoolean("IsBaby", true);
		}

		if (this.isVillager()) {
			tagCompound.setBoolean("IsVillager", true);
		}

		tagCompound.setInteger("ConversionTime",
				this.isConverting() ? this.conversionTime : -1);
		tagCompound.setBoolean("CanBreakDoors", this.func_146072_bX());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);

		if (tagCompund.getBoolean("IsVillager")) {
			this.setVillager(true);
		}

		if (tagCompund.hasKey("ConversionTime", 99)
				&& tagCompund.getInteger("ConversionTime") > -1) {
			this.startConversion(tagCompund.getInteger("ConversionTime"));
		}

		this.func_146070_a(tagCompund.getBoolean("CanBreakDoors"));
	}

	/**
	 * This method gets called when the entity kills another one.
	 */
	public void onKillEntity(EntityLivingBase entityLivingIn) {
		super.onKillEntity(entityLivingIn);

		if ((this.worldObj.getDifficulty() == EnumDifficulty.NORMAL || this.worldObj
				.getDifficulty() == EnumDifficulty.HARD)
				&& entityLivingIn instanceof EntityVillager) {
			if (this.worldObj.getDifficulty() != EnumDifficulty.HARD
					&& this.rand.nextBoolean()) {
				return;
			}

			EntityIncinerator entityzombie = new EntityIncinerator(
					this.worldObj);
			entityzombie.copyLocationAndAnglesFrom(entityLivingIn);
			this.worldObj.removeEntity(entityLivingIn);
			entityzombie.func_180482_a(this.worldObj
					.getDifficultyForLocation(new BlockPos(entityzombie)),
					(IEntityLivingData) null);
			entityzombie.setVillager(true);

			this.worldObj.spawnEntityInWorld(entityzombie);
			this.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1016,
					new BlockPos((int) this.posX, (int) this.posY,
							(int) this.posZ), 0);
		}
	}

	public float getEyeHeight() {
		float f = 1.74F;

		if (this.isChild()) {
			f = (float) ((double) f - 0.81D);
		}

		return f;
	}

	protected boolean func_175448_a(ItemStack p_175448_1_) {
		return p_175448_1_.getItem() == Items.egg && this.isChild()
				&& this.isRiding() ? false : super.func_175448_a(p_175448_1_);
	}

	public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_,
			IEntityLivingData p_180482_2_) {
		Object p_180482_2_1 = super.func_180482_a(p_180482_1_, p_180482_2_);
		float f = p_180482_1_.getClampedAdditionalDifficulty();
		this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);

		if (p_180482_2_1 == null) {
			p_180482_2_1 = new EntityIncinerator.GroupData(
					this.worldObj.rand.nextFloat() < net.minecraftforge.common.ForgeModContainer.zombieBabyChance,
					this.worldObj.rand.nextFloat() < 0.05F, null);
		}

		if (p_180482_2_1 instanceof EntityIncinerator.GroupData) {
			EntityIncinerator.GroupData groupdata = (EntityIncinerator.GroupData) p_180482_2_1;

			if (groupdata.field_142046_b) {
				this.setVillager(true);
			}

			if (groupdata.field_142048_a) {

				if ((double) this.worldObj.rand.nextFloat() < 0.05D) {
					List list = this.worldObj.getEntitiesWithinAABB(
							EntityChicken.class, this.getEntityBoundingBox()
									.expand(5.0D, 3.0D, 5.0D),
							IEntitySelector.IS_STANDALONE);

					if (!list.isEmpty()) {
						EntityChicken entitychicken = (EntityChicken) list
								.get(0);
						entitychicken.setChickenJockey(true);
						this.mountEntity(entitychicken);
					}
				} else if ((double) this.worldObj.rand.nextFloat() < 0.05D) {
					EntityChicken entitychicken1 = new EntityChicken(
							this.worldObj);
					entitychicken1.setLocationAndAngles(this.posX, this.posY,
							this.posZ, this.rotationYaw, 0.0F);
					entitychicken1.func_180482_a(p_180482_1_,
							(IEntityLivingData) null);
					entitychicken1.setChickenJockey(true);
					this.worldObj.spawnEntityInWorld(entitychicken1);
					this.mountEntity(entitychicken1);
				}
			}
		}

		this.func_146070_a(this.rand.nextFloat() < f * 0.1F);
		this.func_180481_a(p_180482_1_);
		this.func_180483_b(p_180482_1_);

		if (this.getEquipmentInSlot(4) == null) {
			Calendar calendar = this.worldObj.getCurrentDate();

			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31
					&& this.rand.nextFloat() < 0.25F) {
				this.setCurrentItemOrArmor(
						4,
						new ItemStack(
								this.rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin
										: Blocks.pumpkin));
				this.equipmentDropChances[4] = 0.0F;
			}
		}

		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
				.applyModifier(
						new AttributeModifier("Random spawn bonus", this.rand
								.nextDouble() * 0.05000000074505806D, 0));
		double d0 = this.rand.nextDouble() * 1.5D * (double) f;

		if (d0 > 1.0D) {
			this.getEntityAttribute(SharedMonsterAttributes.followRange)
					.applyModifier(
							new AttributeModifier("Random zombie-spawn bonus",
									d0, 2));
		}

		if (this.rand.nextFloat() < f * 0.05F) {
			this.getEntityAttribute(reinforcementChance).applyModifier(
					new AttributeModifier("Leader zombie bonus", this.rand
							.nextDouble() * 0.25D + 0.5D, 0));
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
					.applyModifier(
							new AttributeModifier("Leader zombie bonus",
									this.rand.nextDouble() * 3.0D + 1.0D, 2));
			this.func_146070_a(true);
		}

		return (IEntityLivingData) p_180482_2_1;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	public boolean interact(EntityPlayer player) {
		ItemStack itemstack = player.getCurrentEquippedItem();

		if (itemstack != null && itemstack.getItem() == Items.golden_apple
				&& itemstack.getMetadata() == 0 && this.isVillager()
				&& this.isPotionActive(Potion.weakness)) {
			if (!player.capabilities.isCreativeMode) {
				--itemstack.stackSize;
			}

			if (itemstack.stackSize <= 0) {
				player.inventory.setInventorySlotContents(
						player.inventory.currentItem, (ItemStack) null);
			}

			if (!this.worldObj.isRemote) {
				this.startConversion(this.rand.nextInt(2401) + 3600);
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Starts converting this zombie into a villager. The zombie converts into a
	 * villager after the specified time in ticks.
	 */
	protected void startConversion(int p_82228_1_) {
		this.conversionTime = p_82228_1_;
		this.getDataWatcher().updateObject(14, Byte.valueOf((byte) 1));
		this.removePotionEffect(Potion.weakness.id);
		this.addPotionEffect(new PotionEffect(Potion.damageBoost.id,
				p_82228_1_, Math.min(this.worldObj.getDifficulty()
						.getDifficultyId() - 1, 0)));
		this.worldObj.setEntityState(this, (byte) 16);
	}

	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte p_70103_1_) {
		if (p_70103_1_ == 16) {
			if (!this.isSilent()) {
				this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D,
						this.posZ + 0.5D, "mob.zombie.remedy",
						1.0F + this.rand.nextFloat(),
						this.rand.nextFloat() * 0.7F + 0.3F, false);
			}
		} else {
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	protected boolean canDespawn() {
		return !this.isConverting();
	}

	/**
	 * Returns whether this zombie is in the process of converting to a villager
	 */
	public boolean isConverting() {
		return false;
	}

	/**
	 * Convert this zombie into a villager.
	 */
	protected void convertToVillager() {
		// Do nothing, there are no Risen Dead Villagers.
	}

	/**
	 * Return the amount of time decremented from conversionTime every tick.
	 */
	protected int getConversionTimeBoost() {
		int i = 1;

		if (this.rand.nextFloat() < 0.01F) {
			int j = 0;

			for (int k = (int) this.posX - 4; k < (int) this.posX + 4 && j < 14; ++k) {
				for (int l = (int) this.posY - 4; l < (int) this.posY + 4
						&& j < 14; ++l) {
					for (int i1 = (int) this.posZ - 4; i1 < (int) this.posZ + 4
							&& j < 14; ++i1) {
						Block block = this.worldObj.getBlockState(
								new BlockPos(k, l, i1)).getBlock();

						if (block == Blocks.iron_bars || block == Blocks.bed) {
							if (this.rand.nextFloat() < 0.3F) {
								++i;
							}

							++j;
						}
					}
				}
			}
		}

		return i;
	}

	/**
	 * Sets the width and height of the entity. Args: width, height
	 */
	protected final void setSize(float width, float height) {
		boolean flag = this.zombieWidth > 0.0F && this.zombieHeight > 0.0F;
		this.zombieWidth = width;
		this.zombieHeight = height;

		if (!flag) {
			this.multiplySize(2.0F);
		}
	}

	/**
	 * Multiplies the height and width by the provided float.
	 * 
	 * @param size
	 *            The size to multiply the height and width of the entity by.
	 */
	protected final void multiplySize(float size) {
		super.setSize(this.zombieWidth * size, this.zombieHeight * size);
	}

	/**
	 * Returns the Y Offset of this entity.
	 */
	public double getYOffset() {
		return super.getYOffset() - 0.5D;
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
	}

	class GroupData implements IEntityLivingData {
		public boolean field_142048_a;
		public boolean field_142046_b;
		private static final String __OBFID = "CL_00001704";

		private GroupData(boolean p_i2348_2_, boolean p_i2348_3_) {
			this.field_142048_a = false;
			this.field_142046_b = false;
			this.field_142048_a = p_i2348_2_;
			this.field_142046_b = p_i2348_3_;
		}

		GroupData(boolean p_i2349_2_, boolean p_i2349_3_, Object p_i2349_4_) {
			this(p_i2349_2_, p_i2349_3_);
		}
	}

	public int getStrength() {
		return 4;
	}

	class AIFireballAttack extends EntityAIBase {
		private EntityIncinerator field_179470_b = EntityIncinerator.this;
		public int field_179471_a;

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute() {
			return this.field_179470_b.getAttackTarget() != null;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting() {
			this.field_179471_a = 0;
		}

		/**
		 * Updates the task
		 */
		public void updateTask() {
			EntityLivingBase entitylivingbase = this.field_179470_b
					.getAttackTarget();
			double d0 = 64.0D;

			if (entitylivingbase.getDistanceSqToEntity(this.field_179470_b) < d0
					* d0
					&& this.field_179470_b.canEntityBeSeen(entitylivingbase)) {
				World world = this.field_179470_b.worldObj;
				++this.field_179471_a;

				if (this.field_179471_a == 10) {
					world.playAuxSFXAtEntity((EntityPlayer) null, 1007,
							new BlockPos(this.field_179470_b), 0);
				}

				if (this.field_179471_a == 20) {
					double d1 = 4.0D;
					Vec3 vec3 = this.field_179470_b.getLook(1.0F);
					double d2 = entitylivingbase.posX
							- (this.field_179470_b.posX + vec3.xCoord * d1);
					double d3 = entitylivingbase.getEntityBoundingBox().minY
							+ (double) (entitylivingbase.height / 2.0F)
							- (0.5D + this.field_179470_b.posY + (double) (this.field_179470_b.height / 2.0F));
					double d4 = entitylivingbase.posZ
							- (this.field_179470_b.posZ + vec3.zCoord * d1);
					world.playAuxSFXAtEntity((EntityPlayer) null, 1008,
							new BlockPos(this.field_179470_b), 0);
					EntityIncineratorFireball fireball = new EntityIncineratorFireball(
							world, this.field_179470_b, d2, d3, d4);
					fireball.explosionPower = this.field_179470_b.getStrength();
					fireball.posX = this.field_179470_b.posX + vec3.xCoord * d1;
					fireball.posY = this.field_179470_b.posY
							+ (double) (this.field_179470_b.height / 2.0F)
							+ 0.5D;
					fireball.posZ = this.field_179470_b.posZ + vec3.zCoord * d1;
					world.spawnEntityInWorld(fireball);
					this.field_179471_a = -40;
				}
			} else if (this.field_179471_a > 0) {
				--this.field_179471_a;
			}
		}
	}

	class AILightningAttack extends EntityAIBase {
		private EntityIncinerator incinerator = EntityIncinerator.this;
		private int field_179455_b;

		public AILightningAttack() {
			this.setMutexBits(3);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute() {
			EntityLivingBase entitylivingbase = this.incinerator
					.getAttackTarget();
			return entitylivingbase != null && entitylivingbase.isEntityAlive();
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean continueExecuting() {
			return super.continueExecuting();
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting() {
			this.field_179455_b = -10;
			this.incinerator.getNavigator().clearPathEntity();
			this.incinerator.getLookHelper().setLookPositionWithEntity(
					this.incinerator.getAttackTarget(), 90.0F, 90.0F);
			this.incinerator.isAirBorne = true;
		}

		/**
		 * Updates the task
		 */
		public void updateTask() {
			EntityLivingBase entitylivingbase = this.incinerator
					.getAttackTarget();
			this.incinerator.getNavigator().clearPathEntity();
			this.incinerator.getLookHelper().setLookPositionWithEntity(
					entitylivingbase, 90.0F, 90.0F);

			if (!this.incinerator.canEntityBeSeen(entitylivingbase)) {
				this.incinerator.setAttackTarget((EntityLivingBase) null);
			} else {
				++this.field_179455_b;

				if (this.field_179455_b == 0) {
					this.incinerator.worldObj.setEntityState(this.incinerator,
							(byte) 21);
				} else if (this.field_179455_b >= 60) {
					float f = 1.0F;

					if (this.incinerator.worldObj.getDifficulty() == EnumDifficulty.HARD) {
						f += 2.0F;
					}

					EntityLightningBolt bolt = new EntityLightningBolt(
							incinerator.worldObj, entitylivingbase.posX,
							entitylivingbase.posY, entitylivingbase.posZ);
					incinerator.worldObj.spawnEntityInWorld(bolt);
					this.incinerator.setAttackTarget((EntityLivingBase) null);
				} else if (this.field_179455_b >= 60
						&& this.field_179455_b % 20 == 0) {
					;
				}

				super.updateTask();
			}
		}
	}
}