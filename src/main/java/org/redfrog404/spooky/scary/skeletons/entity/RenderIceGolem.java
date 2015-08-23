package org.redfrog404.spooky.scary.skeletons.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderIceGolem extends RenderLiving
{
    private static final ResourceLocation ironGolemTextures = new ResourceLocation("spooky:textures/entity/dim9/ice_golem.png");

    public RenderIceGolem()
    {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelIceGolem(), 0.5F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityIceGolem entity)
    {
        return ironGolemTextures;
    }

    protected void func_180588_a(EntityIceGolem p_180588_1_, float p_180588_2_, float p_180588_3_, float p_180588_4_)
    {
        super.rotateCorpse(p_180588_1_, p_180588_2_, p_180588_3_, p_180588_4_);

        if ((double)p_180588_1_.limbSwingAmount >= 0.01D)
        {
            float f3 = 13.0F;
            float f4 = p_180588_1_.limbSwing - p_180588_1_.limbSwingAmount * (1.0F - p_180588_4_) + 6.0F;
            float f5 = (Math.abs(f4 % f3 - f3 * 0.5F) - f3 * 0.25F) / (f3 * 0.25F);
            GlStateManager.rotate(6.5F * f5, 0.0F, 0.0F, 1.0F);
        }
    }

    protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
    {
        this.func_180588_a((EntityIceGolem)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityIceGolem)entity);
    }
}