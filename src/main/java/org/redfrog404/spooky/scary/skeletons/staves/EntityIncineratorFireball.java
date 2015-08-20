package org.redfrog404.spooky.scary.skeletons.staves;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityIncineratorFireball extends EntityFireball {
	public int explosionPower = 1;
	private boolean isMobBullet;

	public EntityIncineratorFireball(World worldIn, boolean isMobBullet) {
		super(worldIn);
		this.isMobBullet = isMobBullet;
	}
	
	public EntityIncineratorFireball(World worldIn, EntityLivingBase p_i1769_2_, double p_i1769_3_, double p_i1769_5_, double p_i1769_7_)
    {
        super(worldIn, p_i1769_2_, p_i1769_3_, p_i1769_5_, p_i1769_7_);
    }

	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	protected void onImpact(MovingObjectPosition movingObject) {
		makeLava();
		
		if (!this.worldObj.isRemote) {
			if (movingObject.entityHit != null) {
				movingObject.entityHit.attackEntityFrom(DamageSource
						.causeFireballDamage(this, this.shootingEntity), 6.0F);
				this.func_174815_a(this.shootingEntity, movingObject.entityHit);
			}
			
			boolean flag = !isMobBullet ? this.worldObj.getGameRules()
					.getGameRuleBooleanValue("mobGriefing") : false;
			this.worldObj.newExplosion((Entity) null, this.posX, this.posY,
					this.posZ, (float) this.explosionPower, flag, flag);
			this.setDead();
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("ExplosionPower", this.explosionPower);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);

		if (tagCompund.hasKey("ExplosionPower", 99)) {
			this.explosionPower = tagCompund.getInteger("ExplosionPower");
		}
	}

	protected void makeLava() {
		World world = this.worldObj;
		IBlockState lava = Blocks.lava.getBlockState().getBaseState();
		
		world.setBlockState(this.getPosition(), lava);
		world.setBlockState(this.getPosition().add(1, 0, 0), lava);
		world.setBlockState(this.getPosition().add(0, 0, 1), lava);
		world.setBlockState(this.getPosition().add(-1, 0, 0), lava);
		world.setBlockState(this.getPosition().add(0, 0, -1), lava);
		world.setBlockState(this.getPosition().add(0, 1, 0), lava);
		world.setBlockState(this.getPosition().add(0, -1, 0), lava);
		
	}
}