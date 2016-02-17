package org.redfrog404.spooky.scary.skeletons.entity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.redfrog404.spooky.scary.skeletons.entity.entity.EntityEnderBat;
import org.redfrog404.spooky.scary.skeletons.entity.model.ModelEnderBat;

@SideOnly(Side.CLIENT)
public class RenderEnderBat extends RenderLiving
{
    private static final ResourceLocation batTextures = new ResourceLocation("spooky:textures/entity/end/ender_bat.png");

    public RenderEnderBat(RenderManager p_i46192_1_)
    {
        super(p_i46192_1_, new ModelEnderBat(), 0.25F);
    }

    protected ResourceLocation getEntityTexture(EntityEnderBat p_180566_1_)
    {
        return batTextures;
    }

    protected void preRenderCallback(EntityEnderBat p_180567_1_, float p_180567_2_)
    {
        GlStateManager.scale(0.35F, 0.35F, 0.35F);
    }

    protected void rotateCorpse(EntityEnderBat p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
    {
        if (!p_77043_1_.getIsBatHanging())
        {
            GlStateManager.translate(0.0F, MathHelper.cos(p_77043_2_ * 0.3F) * 0.1F, 0.0F);
        }
        else
        {
            GlStateManager.translate(0.0F, -0.1F, 0.0F);
        }

        super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
    {
        this.preRenderCallback((EntityEnderBat)p_77041_1_, p_77041_2_);
    }

    protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
    {
        this.rotateCorpse((EntityEnderBat)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityEnderBat)entity);
    }
}