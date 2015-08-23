package org.redfrog404.spooky.scary.skeletons.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityFrostBall extends EntityFireball
{

    public EntityFrostBall(World worldIn)
    {
        super(worldIn);
        this.setSize(0.3125F, 0.3125F);
    }

    public EntityFrostBall(World worldIn, EntityLivingBase p_i1771_2_, double p_i1771_3_, double p_i1771_5_, double p_i1771_7_)
    {
        super(worldIn, p_i1771_2_, p_i1771_3_, p_i1771_5_, p_i1771_7_);
        this.setSize(0.3125F, 0.3125F);
    }

    public EntityFrostBall(World worldIn, double p_i1772_2_, double p_i1772_4_, double p_i1772_6_, double p_i1772_8_, double p_i1772_10_, double p_i1772_12_)
    {
        super(worldIn, p_i1772_2_, p_i1772_4_, p_i1772_6_, p_i1772_8_, p_i1772_10_, p_i1772_12_);
        this.setSize(0.3125F, 0.3125F);
    }
    
    public void onUpdate(){
    	super.onUpdate();
    	this.extinguish();
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition movingObject)
    {
        if (!this.worldObj.isRemote)
        {
            boolean flag;

            if (movingObject.entityHit != null)
            {
                flag = movingObject.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 5.0F);

                if (flag)
                {
                    this.func_174815_a(this.shootingEntity, movingObject.entityHit);
                }
            }
            else
            {
                flag = true;

                if (this.shootingEntity != null && this.shootingEntity instanceof EntityLiving)
                {
                    flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
                }

                if (flag)
                {
                    BlockPos blockpos = movingObject.getBlockPos().offset(movingObject.sideHit);

                    if (this.worldObj.isAirBlock(blockpos))
                    {
                        this.worldObj.setBlockState(blockpos, Blocks.ice.getDefaultState());
                    }
                }
            }

            this.setDead();
        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }
}